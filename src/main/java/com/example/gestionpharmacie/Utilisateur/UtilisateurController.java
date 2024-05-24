package com.example.gestionpharmacie.Utilisateur;

import com.example.gestionpharmacie.Config.UserAuthenticationProvider;
import com.example.gestionpharmacie.Dto.CredentialsDto;
import com.example.gestionpharmacie.Dto.SignUpDto;
import com.example.gestionpharmacie.Dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/utilisateur")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto user) {
        UserDto createdUser = utilisateurService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/api/utilisateur/" + createdUser.getId())).body(createdUser);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = utilisateurService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }
}
