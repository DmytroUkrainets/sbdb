package com.example.sbdb;

import com.example.sbdb.model.Product;
import com.example.sbdb.model.Supplier;
import com.example.sbdb.repository.ProductRepository;
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
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    void testSaveProduct() {
        Supplier supplier = new Supplier();
        supplier.setName("Tech Supplier");
        supplier.setContact("tech@example.com");
        supplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(1500.0);
        product.setSupplier(supplier);

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Laptop");
    }

    @Test
    void testFindById() {
        Supplier supplier = new Supplier();
        supplier.setName("Tech Supplier");
        supplier.setContact("tech@example.com");
        supplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setName("Smartphone");
        product.setPrice(800.0);
        product.setSupplier(supplier);
        product = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Smartphone");
    }

    @Test
    void testFindAll() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier A");
        supplier.setContact("contact@supplier.com");
        supplier = supplierRepository.save(supplier);

        Product product1 = new Product();
        product1.setName("Monitor");
        product1.setPrice(300.0);
        product1.setSupplier(supplier);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Keyboard");
        product2.setPrice(50.0);
        product2.setSupplier(supplier);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

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
