package com.example.gestionpharmacie.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record CredentialsDto(
        String username,
        char[] password) { }
