package com.example.gestionpharmacie.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String username;
    private String token;
    private String role;
}
