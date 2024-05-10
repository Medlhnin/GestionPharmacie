package com.example.gestionpharmacie.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    public Utilisateur findUtilisateurById(Long requestedId) {
        return utilisateurRepository.findById(requestedId).orElse(null);
    }
    public Utilisateur addUtilisateur(Utilisateur newUtilisateur) {
        return utilisateurRepository.save(newUtilisateur);
    }

}
