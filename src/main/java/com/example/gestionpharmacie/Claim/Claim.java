package com.example.gestionpharmacie.Claim;

import com.example.gestionpharmacie.Users.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Subject;
    private String message;
    private ClaimStatus claimStatus;
    private LocalDateTime claimDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Utilisateur utilisateur;
    public Claim(){
        this.claimDate=LocalDateTime.now();
    }
}
