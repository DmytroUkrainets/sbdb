package com.example.sbdb;

import com.example.sbdb.model.Supplier;
import com.example.sbdb.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SupplierRepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    void testSaveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Tech Supplier");
        supplier.setContact("tech@example.com");

        Supplier savedSupplier = supplierRepository.save(supplier);

        assertThat(savedSupplier.getId()).isNotNull();
        assertThat(savedSupplier.getName()).isEqualTo("Tech Supplier");
    }

    @Test
    void testFindById() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier A");
        supplier.setContact("supplierA@example.com");
        supplier = supplierRepository.save(supplier);

        Optional<Supplier> foundSupplier = supplierRepository.findById(supplier.getId());

        assertThat(foundSupplier).isPresent();
        assertThat(foundSupplier.get().getName()).isEqualTo("Supplier A");
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

