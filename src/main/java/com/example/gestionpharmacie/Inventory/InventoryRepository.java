package com.example.gestionpharmacie.Inventory;

import com.example.gestionpharmacie.Medicament.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByMedicament(Medicament medicament);
}
