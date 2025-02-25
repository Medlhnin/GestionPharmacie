package com.example.gestionpharmacie.Mappers;

import com.example.gestionpharmacie.Dto.SignUpDto;
import com.example.gestionpharmacie.Dto.UserDto;
import com.example.gestionpharmacie.Users.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(Utilisateur user);

    @Mapping(target = "password", ignore = true)
    Utilisateur signUpToUser(SignUpDto signUpDto);

}
