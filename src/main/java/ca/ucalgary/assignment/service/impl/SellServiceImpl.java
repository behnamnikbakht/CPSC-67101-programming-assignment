package ca.ucalgary.assignment.service.impl;

import ca.ucalgary.assignment.domain.Sell;
import ca.ucalgary.assignment.repository.SellRepository;
import ca.ucalgary.assignment.service.SellService;
import ca.ucalgary.assignment.web.rest.NotifierApi;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sell}.
 */
@Service
@Transactional
public class SellServiceImpl implements SellService {

    private final Logger log = LoggerFactory.getLogger(SellServiceImpl.class);

    private final SellRepository sellRepository;

    public SellServiceImpl(SellRepository sellRepository) {
        this.sellRepository = sellRepository;
    }

    @Override
    public Sell save(Sell sell) {
        log.debug("Request to save Sell : {}", sell);
        Sell sold = sellRepository.save(sell);
        NotifierApi.INSTANCE.afterAnySell(sold);
        return sold;
    }

    @Override
    public Optional<Sell> partialUpdate(Sell sell) {
        log.debug("Request to partially update Sell : {}", sell);

        return sellRepository
            .findById(sell.getId())
            .map(existingSell -> {
                if (sell.getCreatedAt() != null) {
                    existingSell.setCreatedAt(sell.getCreatedAt());
                }
                if (sell.getQuantity() != null) {
                    existingSell.setQuantity(sell.getQuantity());
                }

                return existingSell;
            })
            .map(sellRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sell> findAll(Pageable pageable) {
        log.debug("Request to get all Sells");
        return sellRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sell> findOne(Long id) {
        log.debug("Request to get Sell : {}", id);
        return sellRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sell : {}", id);
        sellRepository.deleteById(id);
    }
}
