package com.example.sbdb;


import com.example.sbdb.config.TestContainersConfig;
import com.example.sbdb.model.Supplier;
import com.example.sbdb.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class SupplierContainerTest {
    @Autowired
    private SupplierRepository supplierRepository;

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", TestContainersConfig.MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", TestContainersConfig.MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", TestContainersConfig.MYSQL_CONTAINER::getPassword);
    }

    @Test
    void testSaveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Gadget Supplier");
        supplier.setContact("gadget@example.com");

        Supplier savedSupplier = supplierRepository.save(supplier);

        assertThat(savedSupplier.getId()).isNotNull();
        assertThat(savedSupplier.getName()).isEqualTo("Gadget Supplier");
    }

    @Test
    void testFindById() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier Inc.");
        supplier.setContact("contact@supplier.com");
        supplier = supplierRepository.save(supplier);

        Optional<Supplier> foundSupplier = supplierRepository.findById(supplier.getId());

        assertThat(foundSupplier).isPresent();
        assertThat(foundSupplier.get().getName()).isEqualTo("Supplier Inc.");
    }

    @Test
    void testFindAll() {
        Supplier supplier1 = new Supplier();
        supplier1.setName("Supplier B");
        supplier1.setContact("supplierB@example.com");
        supplierRepository.save(supplier1);

        Supplier supplier2 = new Supplier();
        supplier2.setName("Supplier C");
        supplier2.setContact("supplierC@example.com");
        supplierRepository.save(supplier2);

        List<Supplier> suppliers = supplierRepository.findAll();

        assertThat(suppliers).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testUpdateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Old Supplier");
        supplier.setContact("oldSupplier@example.com");
        supplier = supplierRepository.save(supplier);

        supplier.setName("Updated Supplier");
        supplier.setContact("updatedSupplier@example.com");
        Supplier updatedSupplier = supplierRepository.save(supplier);

        assertThat(updatedSupplier.getName()).isEqualTo("Updated Supplier");
        assertThat(updatedSupplier.getContact()).isEqualTo("updatedSupplier@example.com");
    }

    @Test
    void testDeleteSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier D");
        supplier.setContact("supplierD@example.com");
        supplier = supplierRepository.save(supplier);

        supplierRepository.deleteById(supplier.getId());

        Optional<Supplier> deletedSupplier = supplierRepository.findById(supplier.getId());
        assertThat(deletedSupplier).isEmpty();
    }
}
