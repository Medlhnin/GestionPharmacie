package com.example.gestionpharmacie.Inventory;

import com.example.gestionpharmacie.Medicament.Medicament;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "medicament_id")
    private Medicament medicament;

    private int availableQuantity;

    private LocalDateTime lastUpdated;

    public Inventory() {
        this.lastUpdated = LocalDateTime.now();
    }

    public void retirerDuStock(int quantity) {
        if (this.availableQuantity >= quantity) {
            this.availableQuantity -= quantity;
        } else {
            throw new IllegalStateException("Stock insuffisant !");
        }
        this.lastUpdated = LocalDateTime.now();
    }

    public void ajouterAuStock(int quantity) {
        this.availableQuantity += quantity;
        this.lastUpdated = LocalDateTime.now();
    }
}
