package com.example.gestionpharmacie.mappers;

import com.example.gestionpharmacie.Dto.MedicamentDTO;
import com.example.gestionpharmacie.Medicament.Medicament;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MedicamentMapper {
    Medicament toMedicament(MedicamentDTO medicamentDTO);
}
