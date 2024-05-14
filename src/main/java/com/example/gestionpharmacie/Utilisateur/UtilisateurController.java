package com.example.gestionpharmacie.Utilisateur;

import com.example.gestionpharmacie.Dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService,
                                  AuthenticationManager authenticationManager,
                                  PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<Void> enregistrer(@RequestBody Utilisateur newUtilisateur, UriComponentsBuilder ucb) {
        String hashedPassword = passwordEncoder.encode(newUtilisateur.getPassword());
        newUtilisateur.setPassword(hashedPassword);
        Utilisateur savedUtilisateur = utilisateurService.addUtilisateur(newUtilisateur);
        URI locationOfMedicament = ucb
                .path("utilisateur/{id}")
                .buildAndExpand(savedUtilisateur.getId())
                .toUri();
        return ResponseEntity.created(locationOfMedicament).build();
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse.toString());
    }
}
