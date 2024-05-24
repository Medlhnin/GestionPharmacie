package com.example.gestionpharmacie.Medicament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/medicament")
public class MedicamentController {
    private final MedicamentService medicamentService;
    @Autowired
    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }
    @PostMapping
    public ResponseEntity<Void> addMedicament(@RequestBody Medicament newMedicament, UriComponentsBuilder ucb) {
        Medicament savedMedicament = medicamentService.addMedicament(newMedicament);
        URI locationOfMedicament = ucb
                .path("api/medicament/{id}")
                .buildAndExpand(savedMedicament.getId())
                .toUri();
        return ResponseEntity.created(locationOfMedicament).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMedicament(@RequestBody Medicament medicament, UriComponentsBuilder ucb) {
        Medicament updatedMedicament = medicamentService.updateMedicament(medicament);
        URI locationOfUpdatedCashCard = ucb
                .path("api/medicament/{id}")
                .buildAndExpand(updatedMedicament.getId())
                .toUri();
        return ResponseEntity.created(locationOfUpdatedCashCard).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        medicamentService.deleteMedicament(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Medicament> findById(@PathVariable Long requestedId) {
        Medicament medicament = medicamentService.findMedicamentById(requestedId);
        if (medicament != null) {
            return ResponseEntity.ok(medicament);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping
    public ResponseEntity<List<Medicament>> findAllMedicaments() {
        return ResponseEntity.ok(medicamentService.findAllMedicaments());
    }
}
