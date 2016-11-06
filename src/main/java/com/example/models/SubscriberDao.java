package com.example.models;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class SubscriberDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Subscriber1 subscriber1) {
        entityManager.persist(subscriber1);
    }

    public void delete(Subscriber1 subscriber1) {
        if (entityManager.contains(subscriber1)) {
            entityManager.remove(subscriber1);
        }
    }

    public List<Subscriber1> getAll() {
        return entityManager.createQuery("from Subscriber1 s LEFT JOIN FETCH s.user").getResultList();
    }
    
    public List<User> getAllSubscribedUsers() {
        return getAll().stream().map(i -> i.getUser()).collect(Collectors.toList());
    }
    
    public Subscriber1 getById(long id) {
        return entityManager.find(Subscriber1.class, id);
    }

    public void update(Subscriber1 subscriber1) {
        entityManager.merge(subscriber1);
    }

}
