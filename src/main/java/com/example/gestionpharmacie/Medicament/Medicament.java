package com.example.gestionpharmacie.Medicament;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicament {
    @Id
    private Long id;
    private String nom;
    private String description;
    private String type;
    private String prix;
    private String quantite;
    private LocalDateTime dateExpiration;
    private String maladieCible;
    private String maladieChronique;
    private String image;
}
