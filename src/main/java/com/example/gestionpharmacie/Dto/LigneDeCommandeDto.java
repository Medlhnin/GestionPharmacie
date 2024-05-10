package com.example.gestionpharmacie.Dto;

import com.example.gestionpharmacie.Medicament.Medicament;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneDeCommandeDto {
    private int quantite;
    private Long medicamentId;
}
