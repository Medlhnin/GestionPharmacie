package com.example.gestionpharmacie.Claim;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findByClaimStatus(ClaimStatus claimStatus);
}
