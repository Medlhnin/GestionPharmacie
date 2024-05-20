package com.example.gestionpharmacie.mappers;

import com.example.gestionpharmacie.Dto.SignUpDto;
import com.example.gestionpharmacie.Dto.UserDto;
import com.example.gestionpharmacie.Utilisateur.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(Utilisateur user);

    @Mapping(target = "password", ignore = true)
    Utilisateur signUpToUser(SignUpDto signUpDto);
}
