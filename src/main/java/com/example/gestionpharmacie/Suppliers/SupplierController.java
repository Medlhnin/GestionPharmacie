package com.example.gestionpharmacie.Suppliers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierRepository supplierRepository;
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @PostMapping
    public ResponseEntity<Void> addSupplier(@RequestBody Supplier newSupplier,
                                               UriComponentsBuilder ucb) {
        Supplier supplier = new Supplier(null,
                newSupplier.getName(),
                newSupplier.getAddress(),
                newSupplier.getPhoneNumber(),
                newSupplier.getEmail(),
                newSupplier.getWebsite());
        Supplier savedSupplier = supplierRepository.save(supplier);
        URI locationOfNewSupplier = ucb
                .path("/api/v1/suppliers/{id}")
                .buildAndExpand(savedSupplier.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewSupplier).build();
    }

    @GetMapping()
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable Long requestedId){
        Supplier supplier = supplierRepository.findById(requestedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier NOT FOUND."));

        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<Void> putSupplier(@PathVariable Long requestedId,
                                             @RequestBody Supplier supplierUpdate) {
        Optional<Supplier> optionalSupplier= supplierRepository.findById(requestedId);
        if (optionalSupplier.isPresent()) {
            Supplier supplier = optionalSupplier.get();
            Supplier updatedSupplier = new Supplier(supplier.getId(),
                    supplierUpdate.getName(),
                    supplierUpdate.getAddress(),
                    supplierUpdate.getPhoneNumber(),
                    supplierUpdate.getEmail(),
                    supplierUpdate.getWebsite());

            supplierRepository.save(updatedSupplier);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!supplierRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        supplierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
