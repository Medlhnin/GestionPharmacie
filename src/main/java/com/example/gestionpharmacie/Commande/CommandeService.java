package com.example.gestionpharmacie.Commande;

import com.example.gestionpharmacie.Dto.CommandeDto;
import com.example.gestionpharmacie.Medicament.Medicament;
import com.example.gestionpharmacie.Medicament.MedicamentService;
import com.example.gestionpharmacie.Utilisateur.Utilisateur;
import com.example.gestionpharmacie.Utilisateur.UtilisateurService;
import com.example.gestionpharmacie.ligneDeCommande.LigneDeCommande;
import com.example.gestionpharmacie.ligneDeCommande.ligneDeCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final ligneDeCommandeRepository ligneDeCommandeRepository;
    private final UtilisateurService utilisateurService;
    private final MedicamentService medicamentService;
    @Autowired
    public CommandeService(CommandeRepository commandeRepository,ligneDeCommandeRepository ligneDeCommandeRepository, UtilisateurService utilisateurService, MedicamentService medicamentService) {
        this.commandeRepository = commandeRepository;
        this.ligneDeCommandeRepository = ligneDeCommandeRepository;
        this.utilisateurService = utilisateurService;
        this.medicamentService = medicamentService;
    }
    public Commande createCommande(CommandeDto commandeDto) {
        Commande newCommande = new Commande();
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(commandeDto.getUtilisateurId());
        newCommande.setDate(commandeDto.getDate());
        newCommande.setEtat(commandeDto.getEtat());
        newCommande.setMontantTotal(commandeDto.getMontantTotal());
        newCommande.setModePaiement(commandeDto.getModePaiement());
        newCommande.setAdresse(commandeDto.getAdresse());
        newCommande.setUtilisateur(utilisateur);
        commandeDto.getLignesDeCommandeDto().forEach(ligneDeCommandeDto -> {
            LigneDeCommande ligneDeCommande = new LigneDeCommande();
            ligneDeCommande.setQuantite(ligneDeCommandeDto.getQuantite());
            Medicament medicament = medicamentService.findMedicamentById(ligneDeCommandeDto.getMedicamentId());
            ligneDeCommande.setMedicament(medicament);
            ligneDeCommande.setCommande(newCommande);
            newCommande.getLignesDeCommande().add(ligneDeCommande);
        });
        commandeRepository.save(newCommande);
        return newCommande;
    }
}
