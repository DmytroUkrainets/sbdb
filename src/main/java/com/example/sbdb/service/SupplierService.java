package com.example.sbdb.service;

import com.example.sbdb.model.Supplier;
import com.example.sbdb.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public List<Supplier> getAllSuppliers() {
        return repository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Supplier saveSupplier(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Optional<Supplier> existingSupplier = repository.findById(id);
        if (existingSupplier.isPresent()) {
            Supplier supplier = existingSupplier.get();
            supplier.setName(supplierDetails.getName());
            supplier.setContact(supplierDetails.getContact());
            return repository.save(supplier);
        } else {
            throw new RuntimeException("Supplier not found");
        }
    }

    public void deleteSupplier(Long id) {
        repository.deleteById(id);
    }
}
