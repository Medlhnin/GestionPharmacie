package com.example.gestionpharmacie.Utilisateur;

import com.example.gestionpharmacie.Dto.CredentialsDto;
import com.example.gestionpharmacie.Dto.SignUpDto;
import com.example.gestionpharmacie.Dto.UserDto;
import com.example.gestionpharmacie.exceptions.AppException;
import com.example.gestionpharmacie.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        Utilisateur user = utilisateurRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<Utilisateur> optionalUser = utilisateurRepository.findByUsername(userDto.username());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        if(userDto.email()==null || userDto.email().isEmpty()){
            throw new AppException("Email is required", HttpStatus.BAD_REQUEST);
        }

        if(userDto.nom() == null || userDto.nom().isEmpty()){
            throw new AppException("Nom is required", HttpStatus.BAD_REQUEST);
        }

        if(userDto.prenom() == null || userDto.prenom().isEmpty()){
            throw new AppException("Prenom is required", HttpStatus.BAD_REQUEST);
        }

        Utilisateur user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));
        user.setRole("USER");
        user.setEmail(userDto.email());
        Utilisateur savedUser = utilisateurRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByLogin(String username) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }



}
