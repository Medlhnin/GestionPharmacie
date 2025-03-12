package com.example.gestionpharmacie.Claim;

import com.example.gestionpharmacie.Dto.ClaimRequest;
import com.example.gestionpharmacie.Users.UserRepository;
import com.example.gestionpharmacie.Users.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<Void> MakeClaim(@RequestHeader("X-User") String username,
                                          @RequestBody ClaimRequest claimRequest,
                                          UriComponentsBuilder ucb){
        Claim claim = new Claim();
        claim.setSubject(claimRequest.subject());
        claim.setMessage(claimRequest.message());
        claim.setClaimStatus(ClaimStatus.NEW);
        Utilisateur user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        claim.setUtilisateur(user);
        Claim savedClaim = claimRepository.save(claim);
        URI locationOfClaim= ucb
                .path("api/claims/{id}")
                .buildAndExpand(savedClaim.getId())
                .toUri();
        return ResponseEntity.created(locationOfClaim).build();
    }

    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims(){
        List<Claim> claims = claimRepository.findAll();
        return ResponseEntity.ok(claims);
    }

    @PutMapping("/{claimId}/settle")
    public ResponseEntity<String> settleClaim(@PathVariable Long claimId) {
        Claim claim = claimService.sellteClaim(claimId);

        if (claim != null) {
            return ResponseEntity.ok("The claim with ID " + claimId + " has been successfully settled.");
        } else {
            return ResponseEntity.badRequest().body("Failed to settle the claim with ID " + claimId + ".");
        }
    }

    @GetMapping("/new-claims")
    public ResponseEntity<List<Claim>> getNewClaims() {
        List<Claim> newClaims = claimRepository.findByClaimStatus(ClaimStatus.NEW); // Fetch only non-settled claims

        if (newClaims.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Return empty list if no new claims
        }
        return ResponseEntity.ok(newClaims);
    }




}
