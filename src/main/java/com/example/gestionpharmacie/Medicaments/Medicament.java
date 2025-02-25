package com.example.gestionpharmacie.Medicaments;

import com.example.gestionpharmacie.Inventory.Inventory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String type;
    private double price;
    private LocalDateTime dateExpiration;
    @OneToOne(mappedBy = "medicament", cascade = CascadeType.ALL)
    @JsonIgnore
    private Inventory inventory;
}
