package com.example.gestionpharmacie.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    @GetMapping("/{requestedId}")
    public ResponseEntity<Utilisateur> findById(@PathVariable Long requestedId) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(requestedId);
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> Enregistrer(@RequestBody Utilisateur newUtilisateur, UriComponentsBuilder ucb) {
        Utilisateur savedUtilisateur = utilisateurService.addUtilisateur(newUtilisateur);
        URI locationOfMedicament = ucb
                .path("utilisateur/{id}")
                .buildAndExpand(savedUtilisateur.getId())
                .toUri();
        return ResponseEntity.created(locationOfMedicament).build();
    }
}
