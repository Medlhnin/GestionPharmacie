package com.example.gestionpharmacie.Medicament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentService {
    private final MedicamentRepository medicamentRepository;
    @Autowired
    public MedicamentService(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }
    public Medicament addMedicament(Medicament medicament) {
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
