package com.example.sbdb.service;

import com.example.sbdb.model.Product;
import com.example.sbdb.model.Supplier;
import com.example.sbdb.repository.ProductRepository;
import com.example.sbdb.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());

            Supplier supplier = supplierRepository.findById(productDetails.getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            product.setSupplier(supplier);

            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

