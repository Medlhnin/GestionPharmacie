package com.example.gestionpharmacie.Medicaments;

import com.example.gestionpharmacie.Inventory.Inventory;
import com.example.gestionpharmacie.Inventory.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentService {
    private final MedicamentRepository medicamentRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public MedicamentService(MedicamentRepository medicamentRepository, InventoryRepository inventoryRepository) {
        this.medicamentRepository = medicamentRepository;
        this.inventoryRepository = inventoryRepository;
    }
    public Medicament addMedicament(Medicament medicament, int initialStock) {
        Medicament savedMedicament = medicamentRepository.save(medicament);
        Inventory inventory = new Inventory();
        inventory.setMedicament(savedMedicament);
        inventory.setAvailableQuantity(initialStock);

        inventoryRepository.save(inventory);

        savedMedicament.setInventory(inventory);

        return medicamentRepository.save(medicament);
    }
    public Medicament updateMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }
    public void deleteMedicament(Long id) {
        medicamentRepository.deleteById(id);
    }
    public Medicament findMedicamentById(Long id) {
        Optional<Medicament> medicamentOptional = medicamentRepository.findMedicamentById(id);
        if (medicamentOptional.isPresent()) {
            return medicamentOptional.get();
        }
        else {
            return null;
        }
    }
    public List<Medicament> findAllMedicaments() {
        return medicamentRepository.findAll();
    }


}
