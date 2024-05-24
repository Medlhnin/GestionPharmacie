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
            UserDto userDto = userMapper.toUserDto(user);
            if(userDto.getRole() == null){
                userDto.setRole(user.getRole());
            }
            if(userDto.getEmail() == null){
                userDto.setEmail(user.getEmail());
            }
            return userDto;
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
        user.setEmail(userDto.email());
        user.setRole(userDto.role());
        Utilisateur savedUser = utilisateurRepository.save(user);
        UserDto userDto1 = userMapper.toUserDto(savedUser);
        userDto1.setEmail(userDto.email());
        userDto1.setRole(userDto.role());
        return userDto1;
    }

    public UserDto findByLogin(String username) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }



}
