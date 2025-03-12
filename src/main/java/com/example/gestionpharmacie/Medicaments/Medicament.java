package com.example.gestionpharmacie.Medicaments;

import com.example.gestionpharmacie.Inventory.Inventory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String type;
    private double price;
    private LocalDate dateExpiration;
    @OneToOne(mappedBy = "medicament", cascade = CascadeType.ALL)
    private Inventory inventory;
}
