package ca.ucalgary.assignment.web.rest;

import ca.ucalgary.assignment.domain.Need;
import ca.ucalgary.assignment.domain.Person;
import ca.ucalgary.assignment.domain.Sell;
import ca.ucalgary.assignment.domain.User;
import ca.ucalgary.assignment.repository.NeedRepository;
import ca.ucalgary.assignment.repository.PersonRepository;
import ca.ucalgary.assignment.service.dto.AvailableNotifyEvent;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.persistence.PostPersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifier")
public class NotifierApi implements HandlerInterceptor {

    public static NotifierApi INSTANCE;

    private final Logger log = LoggerFactory.getLogger(NotifierApi.class);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final NeedRepository needRepository;
    private final PersonRepository personRepository;

    private final Map<Long, SseEmitter> connections = new HashMap<>();

    public NotifierApi(NeedRepository needRepository, PersonRepository personRepository) {
        this.needRepository = needRepository;
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void init() {
        INSTANCE = this;
        Runtime
            .getRuntime()
            .addShutdownHook(
                new Thread(() -> {
                    executor.shutdown();
                    try {
                        executor.awaitTermination(1, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        log.error(e.toString());
                    }
                })
            );
    }

    public void afterAnySell(Sell sell) {
        log.info("afterAnySell sell = " + sell);
        List<Need> needs = needRepository.findAll(Example.of(new Need().item(sell.getItem())));
        log.info("afterAnySell needs = " + needs);
        if (needs == null) {
            return;
        }
        for (Need need : needs) {
            Long personId = need.getPerson().getId();
            SseEmitter sseEmitter = connections.get(personId);
            if (sseEmitter == null) {
                log.info("afterAnySell client is not listening " + personId);
                return;
            }
            try {
                log.info("send " + sell + ", to person " + personId);
                sseEmitter.send(new AvailableNotifyEvent().item(sell));
            } catch (Exception e) {
                log.error("afterAnySell", e);
            }
        }
    }

    @GetMapping("/{userId}")
    @CrossOrigin
    public SseEmitter startStream(@PathVariable(value = "userId", required = false) Long userId) {
        log.info("startStream for user " + userId);

        Person person = personRepository.findByUserId(userId).orElse(null);

        log.info("startStream person = " + person);

        if (person == null) {
            return null;
        }

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        connections.put(person.getId(), sseEmitter);

        /*executor.execute(() -> {
            while(true){
                try {
                    sseEmitter.send(new Date());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("afterAnySell", e);
                    break;
                }
            }
        });*/

        return sseEmitter;
    }
}
