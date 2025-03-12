package com.example.gestionpharmacie.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class MedicamentDTO {
    private String name;
    private String description;
    private String type;
    private double price;
    private LocalDate dateExpiration;
    private int availableQuantity;

}
