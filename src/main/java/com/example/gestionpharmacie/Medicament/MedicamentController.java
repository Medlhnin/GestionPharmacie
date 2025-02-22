package com.example.gestionpharmacie.Medicament;

import com.example.gestionpharmacie.Dto.MedicamentDTO;
import com.example.gestionpharmacie.mappers.MedicamentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final MedicamentMapper medicamentMapper;

    @Autowired
    public MedicamentController(MedicamentService medicamentService,
                                MedicamentMapper medicamentMapper) {
        this.medicamentService = medicamentService;
        this.medicamentMapper = medicamentMapper;
    }

    @PostMapping
    public ResponseEntity<Void> addMedicament(@RequestBody MedicamentDTO medicamentDTO, UriComponentsBuilder ucb) {
        Medicament medicament = new Medicament();
        medicament = medicamentMapper.toMedicament(medicamentDTO);
        medicament.setName(medicamentDTO.getName()); // Problème recontré au niveau de l'attibut name
        Medicament savedMedicament = medicamentService.addMedicament(medicament, medicamentDTO.getAvailableQuantity());
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
