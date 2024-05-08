package com.example.gestionpharmacie.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Utilisateur {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String role;
    private String maladieChronique;
}
