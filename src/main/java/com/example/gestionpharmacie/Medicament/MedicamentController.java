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
import java.util.Optional;

@Controller
@RequestMapping("/medicaments")
public class MedicamentController {
    private final MedicamentService medicamentService;
    @Autowired
    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }
    @PostMapping
    public ResponseEntity<Void> addMedicament(@RequestBody Medicament newMedicament, UriComponentsBuilder ucb) {
        Medicament savedMedicament = medicamentService.addMedicament(newMedicament);
        URI locationOfNewCashCard = ucb
                .path("medicaments/{id}")
                .buildAndExpand(savedMedicament.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMedicament(@RequestBody Medicament medicament, UriComponentsBuilder ucb) {
        Medicament updatedMedicament = medicamentService.updateMedicament(medicament);
        URI locationOfUpdatedCashCard = ucb
                .path("medicaments/{id}")
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
    private ResponseEntity<Medicament> findById(@PathVariable Long requestedId) {
        Medicament medicament = medicamentService.findMedicamentById(requestedId);
        if (medicament != null) {
            return ResponseEntity.ok(medicament);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
}
