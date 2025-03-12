package com.example.gestionpharmacie.Users;

import com.example.gestionpharmacie.Config.UserAuthenticationProvider;
import com.example.gestionpharmacie.Dto.CredentialsDto;
import com.example.gestionpharmacie.Dto.SignUpDto;
import com.example.gestionpharmacie.Dto.UserDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/api/user/" + createdUser.getId())).body(createdUser);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestBody String idToken) {
        try {
            // Vérifier le token Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            return ResponseEntity.ok("User authenticated! : " + uid);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body("Invalid Token!");
        }
    }
}
