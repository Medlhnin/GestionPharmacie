package com.example.gestionpharmacie.ligneDeCommande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ligneDeCommandeRepository extends JpaRepository<LigneDeCommande, Long>{
}
