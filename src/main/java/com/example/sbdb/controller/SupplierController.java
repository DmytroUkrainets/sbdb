package com.example.sbdb.controller;

import com.example.sbdb.model.Supplier;
import com.example.sbdb.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Supplier> getAll() {
        return service.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable Long id) {
        return service.getSupplierById(id);
    }

    @PostMapping
    public Supplier create(@RequestBody Supplier supplier) {
        return service.saveSupplier(supplier);
    }

    @PutMapping("/{id}")
    public Supplier update(@PathVariable Long id, @RequestBody Supplier supplier) {
        return service.updateSupplier(id, supplier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSupplier(id);
    }
}

