package com.example.sbdb;

import com.example.sbdb.model.Product;
import com.example.sbdb.model.Supplier;
import com.example.sbdb.service.EntityManagerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EntityManagerServiceTest {

    @Autowired
    private EntityManagerService entityManagerService;

    @PersistenceContext
    private EntityManager entityManager;

    private Supplier createAndSaveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Test Supplier");
        supplier.setContact("supplier@example.com");
        entityManager.persist(supplier);
        entityManager.flush();
        return supplier;
    }

    @Test
    @Transactional
    void testPersistProduct() {
        Supplier supplier = createAndSaveSupplier();

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(1200.0);
        product.setSupplier(supplier);

        entityManagerService.persistProduct(product);
        entityManager.flush();

        Product found = entityManagerService.findProduct(product.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Laptop");
    }

    @Test
    @Transactional
    void testFindProduct() {
        Supplier supplier = createAndSaveSupplier();

        Product product = new Product();
        product.setName("Tablet");
        product.setPrice(500.0);
        product.setSupplier(supplier);

        entityManager.persist(product);
        entityManager.flush();

        Product found = entityManagerService.findProduct(product.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Tablet");
    }

    @Test
    @Transactional
    void testDetachProduct() {
        Supplier supplier = createAndSaveSupplier();

        Product product = new Product();
        product.setName("Phone");
        product.setPrice(900.0);
        product.setSupplier(supplier);

        entityManager.persist(product);
        entityManager.flush();
        entityManagerService.detachProduct(product);

        assertThat(entityManager.contains(product)).isFalse();
    }

    @Test
    @Transactional
    void testMergeProduct() {
        Supplier supplier = createAndSaveSupplier();

        Product product = new Product();
        product.setName("Monitor");
        product.setPrice(300.0);
        product.setSupplier(supplier);

        entityManager.persist(product);
        entityManager.flush();
        entityManager.detach(product);

        product.setPrice(350.0);
        Product merged = entityManagerService.mergeProduct(product);

        assertThat(merged.getPrice()).isEqualTo(350.0);
    }

    @Test
    @Transactional
    void testRemoveProduct() {
        Supplier supplier = createAndSaveSupplier();

        Product product = new Product();
        product.setName("Keyboard");
        product.setPrice(50.0);
        product.setSupplier(supplier);

        entityManager.persist(product);
        entityManager.flush();

        entityManagerService.removeProduct(product);
        entityManager.flush();

        Product found = entityManagerService.findProduct(product.getId());
        assertThat(found).isNull();
    }

    @Test
    @Transactional
    void testRefreshProduct() {
        Supplier supplier = createAndSaveSupplier();

        Product product = new Product();
        product.setName("Mouse");
        product.setPrice(25.0);
        product.setSupplier(supplier);

        entityManager.persist(product);
        entityManager.flush();

        product.setPrice(30.0);
        entityManagerService.refreshProduct(product);

        assertThat(product.getPrice()).isEqualTo(25.0);
    }
}
