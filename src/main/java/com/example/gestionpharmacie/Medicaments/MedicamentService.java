package com.example.gestionpharmacie.Medicaments;

import com.example.gestionpharmacie.Dto.MedicamentDTO;
import com.example.gestionpharmacie.Inventory.Inventory;
import com.example.gestionpharmacie.Inventory.InventoryRepository;
import com.example.gestionpharmacie.Mappers.MedicamentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicamentService {
    private final MedicamentRepository medicamentRepository;
    private final InventoryRepository inventoryRepository;


    public Medicament addMedicament(Medicament medicament, int initialStock) {
        Medicament savedMedicament = medicamentRepository.save(medicament);
        Inventory inventory = new Inventory();
        inventory.setMedicament(savedMedicament);
        inventory.setAvailableQuantity(initialStock);

        inventoryRepository.save(inventory);

        savedMedicament.setInventory(inventory);

        return medicamentRepository.save(medicament);
    }
    public Medicament updateMedicament(Long id, MedicamentDTO updatedMedicament) {
        Medicament medicament = medicamentRepository.findMedicamentById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicament NOT FOUND."));
        medicament.setName(updatedMedicament.getName());
        medicament.setPrice(updatedMedicament.getPrice());
        medicament.setType(updatedMedicament.getType());
        medicament.setDescription(updatedMedicament.getDescription());
        medicament.setDateExpiration(updatedMedicament.getDateExpiration());
        Inventory inventory = inventoryRepository.findByMedicament(medicament)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory NOT FOUND."));
        inventory.setAvailableQuantity(updatedMedicament.getAvailableQuantity());
        inventory.setLastUpdated(LocalDateTime.now());
        inventoryRepository.save(inventory);
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
