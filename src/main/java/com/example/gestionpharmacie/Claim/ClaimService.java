package com.example.gestionpharmacie.Claim;

import com.example.gestionpharmacie.Exceptions.AppException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClaimService {
    private final ClaimRepository claimRepository;

    public Claim sellteClaim(Long id){
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new AppException("claim not found", HttpStatus.NOT_FOUND));
        claim.setClaimStatus(ClaimStatus.SETTLED);
        return claimRepository.save(claim);
    }


}
