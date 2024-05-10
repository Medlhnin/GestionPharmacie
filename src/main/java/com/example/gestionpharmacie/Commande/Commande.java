package com.example.gestionpharmacie.Commande;

import com.example.gestionpharmacie.Utilisateur.Utilisateur;
import com.example.gestionpharmacie.ligneDeCommande.LigneDeCommande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String etat;
    private String montantTotal;
    private String modePaiement;
    private String adresse;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur Utilisateur;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneDeCommande> lignesDeCommande = new ArrayList<>();
}
