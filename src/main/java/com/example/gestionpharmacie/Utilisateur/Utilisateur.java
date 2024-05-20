package com.example.gestionpharmacie.Utilisateur;

import com.example.gestionpharmacie.Commande.Commande;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    @Column(nullable = false)
    @Size(max = 100)
    private String password;
    private String role;
    @OneToMany
    private List<Commande> commandes = new ArrayList<>();

}
