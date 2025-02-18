package com.example.sbdb;

import com.example.sbdb.config.TestContainersConfig;
import com.example.sbdb.model.Product;
import com.example.sbdb.model.Supplier;
import com.example.sbdb.repository.ProductRepository;
import com.example.sbdb.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class ProductContainerTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", TestContainersConfig.MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", TestContainersConfig.MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", TestContainersConfig.MYSQL_CONTAINER::getPassword);
    }

    @Test
    void testSaveProduct() {
        Supplier supplier = new Supplier();
        supplier.setName("Test Supplier");
        supplier.setContact("supplier@example.com");
        supplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setName("Phone");
        product.setPrice(999.99);
        product.setSupplier(supplier);

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Phone");
    }

    @Test
    void testFindById() {
        Supplier supplier = new Supplier();
        supplier.setName("Tech Supplier");
        supplier.setContact("contact@techsupplier.com");
        supplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setName("Headphones");
        product.setPrice(199.99);
        product.setSupplier(supplier);
        product = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Headphones");
    }

    @Test
    void testFindAll() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier A");
        supplier.setContact("contact@supplier.com");
        supplier = supplierRepository.save(supplier);

        Product product1 = new Product();
        product1.setName("Laptop");
        product1.setPrice(1500.0);
        product1.setSupplier(supplier);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setPrice(25.0);
        product2.setSupplier(supplier);
        productRepository.save(product2);

        Iterable<Product> products = productRepository.findAll();

        assertThat(products).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testUpdateProduct() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier B");
        supplier.setContact("supplierB@example.com");
        supplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setName("Tablet");
        product.setPrice(500.0);
        product.setSupplier(supplier);
        product = productRepository.save(product);

        product.setName("Updated Tablet");
        product.setPrice(550.0);
        Product updatedProduct = productRepository.save(product);

        assertThat(updatedProduct.getName()).isEqualTo("Updated Tablet");
        assertThat(updatedProduct.getPrice()).isEqualTo(550.0);
    }

    @Test
    void testDeleteProduct() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier C");
        supplier.setContact("supplierC@example.com");
        supplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setName("Smartwatch");
        product.setPrice(250.0);
        product.setSupplier(supplier);
        product = productRepository.save(product);

        productRepository.deleteById(product.getId());

        Optional<Product> deletedProduct = productRepository.findById(product.getId());
        assertThat(deletedProduct).isEmpty();
    }
}
