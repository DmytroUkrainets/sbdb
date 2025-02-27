package com.example.sbdb.service;

import com.example.sbdb.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EntityManagerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persistProduct(Product product) {
        entityManager.persist(product);
    }

    @Transactional
    public Product findProduct(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Transactional
    public void detachProduct(Product product) {
        entityManager.detach(product);
    }

    @Transactional
    public Product mergeProduct(Product product) {
        return entityManager.merge(product);
    }

    @Transactional
    public void removeProduct(Product product) {
        entityManager.remove(entityManager.contains(product) ? product : entityManager.merge(product));
    }

    @Transactional
    public void refreshProduct(Product product) {
        entityManager.refresh(product);
    }
}
