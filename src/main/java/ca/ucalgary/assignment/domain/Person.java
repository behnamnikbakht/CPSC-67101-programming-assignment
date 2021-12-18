package ca.ucalgary.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User person;

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "items", "createdBy", "subscribedPersons" }, allowSetters = true)
    private Set<ShoppingGroup> shoppingGroups = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "group", "owner", "interestedPersons", "sellerPersons" }, allowSetters = true)
    private Set<Item> items = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_person__interests",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "interests_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "group", "owner", "interestedPersons", "sellerPersons" }, allowSetters = true)
    private Set<Item> interests = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_person__subscriptions",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "subscriptions_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "items", "createdBy", "subscribedPersons" }, allowSetters = true)
    private Set<ShoppingGroup> subscriptions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_person__sells",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "sells_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "group", "owner", "interestedPersons", "sellerPersons" }, allowSetters = true)
    private Set<Item> sells = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPerson() {
        return this.person;
    }

    public void setPerson(User user) {
        this.person = user;
    }

    public Person person(User user) {
        this.setPerson(user);
        return this;
    }

    public Set<ShoppingGroup> getShoppingGroups() {
        return this.shoppingGroups;
    }

    public void setShoppingGroups(Set<ShoppingGroup> shoppingGroups) {
        if (this.shoppingGroups != null) {
            this.shoppingGroups.forEach(i -> i.setCreatedBy(null));
        }
        if (shoppingGroups != null) {
            shoppingGroups.forEach(i -> i.setCreatedBy(this));
        }
        this.shoppingGroups = shoppingGroups;
    }

    public Person shoppingGroups(Set<ShoppingGroup> shoppingGroups) {
        this.setShoppingGroups(shoppingGroups);
        return this;
    }

    public Person addShoppingGroup(ShoppingGroup shoppingGroup) {
        this.shoppingGroups.add(shoppingGroup);
        shoppingGroup.setCreatedBy(this);
        return this;
    }

    public Person removeShoppingGroup(ShoppingGroup shoppingGroup) {
        this.shoppingGroups.remove(shoppingGroup);
        shoppingGroup.setCreatedBy(null);
        return this;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setOwner(null));
        }
        if (items != null) {
            items.forEach(i -> i.setOwner(this));
        }
        this.items = items;
    }

    public Person items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public Person addItem(Item item) {
        this.items.add(item);
        item.setOwner(this);
        return this;
    }

    public Person removeItem(Item item) {
        this.items.remove(item);
        item.setOwner(null);
        return this;
    }

    public Set<Item> getInterests() {
        return this.interests;
    }

    public void setInterests(Set<Item> items) {
        this.interests = items;
    }

    public Person interests(Set<Item> items) {
        this.setInterests(items);
        return this;
    }

    public Person addInterests(Item item) {
        this.interests.add(item);
        item.getInterestedPersons().add(this);
        return this;
    }

    public Person removeInterests(Item item) {
        this.interests.remove(item);
        item.getInterestedPersons().remove(this);
        return this;
    }

    public Set<ShoppingGroup> getSubscriptions() {
        return this.subscriptions;
    }

    public void setSubscriptions(Set<ShoppingGroup> shoppingGroups) {
        this.subscriptions = shoppingGroups;
    }

    public Person subscriptions(Set<ShoppingGroup> shoppingGroups) {
        this.setSubscriptions(shoppingGroups);
        return this;
    }

    public Person addSubscriptions(ShoppingGroup shoppingGroup) {
        this.subscriptions.add(shoppingGroup);
        shoppingGroup.getSubscribedPersons().add(this);
        return this;
    }

    public Person removeSubscriptions(ShoppingGroup shoppingGroup) {
        this.subscriptions.remove(shoppingGroup);
        shoppingGroup.getSubscribedPersons().remove(this);
        return this;
    }

    public Set<Item> getSells() {
        return this.sells;
    }

    public void setSells(Set<Item> items) {
        this.sells = items;
    }

    public Person sells(Set<Item> items) {
        this.setSells(items);
        return this;
    }

    public Person addSells(Item item) {
        this.sells.add(item);
        item.getSellerPersons().add(this);
        return this;
    }

    public Person removeSells(Item item) {
        this.sells.remove(item);
        item.getSellerPersons().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            "}";
    }
}
