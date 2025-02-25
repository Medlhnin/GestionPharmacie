package com.example.gestionpharmacie.Mappers;

import com.example.gestionpharmacie.Dto.MedicamentDTO;
import com.example.gestionpharmacie.Medicaments.Medicament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MedicamentMapper {

    Medicament toMedicament(MedicamentDTO medicamentDTO);
}
