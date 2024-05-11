package com.example.gestionpharmacie.ligneDeCommande;

import com.example.gestionpharmacie.Commande.Commande;
import com.example.gestionpharmacie.Medicament.Medicament;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneDeCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantiteDemandee;
    @OneToOne
    @JoinColumn(name = "medicament_id")
    private Medicament medicament;
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
}
