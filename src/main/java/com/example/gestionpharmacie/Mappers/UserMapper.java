package com.example.gestionpharmacie.Mappers;

import com.example.gestionpharmacie.Dto.SignUpDto;
import com.example.gestionpharmacie.Dto.UserDto;
import com.example.gestionpharmacie.Utilisateur.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(Utilisateur user);


    Utilisateur signUpToUser(SignUpDto signUpDto);

}
