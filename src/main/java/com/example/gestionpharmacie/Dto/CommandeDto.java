package com.example.gestionpharmacie.Dto;

import com.example.gestionpharmacie.ligneDeCommande.LigneDeCommande;
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
public class CommandeDto {
    private LocalDateTime date;
    private String etat;
    private String montantTotal;
    private String modePaiement;
    private String adresse;
    private Long utilisateurId;
    private List<LigneDeCommandeDto> lignesDeCommandeDto = new ArrayList<>();
}
