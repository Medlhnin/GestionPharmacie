package com.example.gestionpharmacie.Users;

import com.example.gestionpharmacie.Orders.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    @Column(nullable = false)
    @Size(max = 100)
    @JsonIgnore
    private String password;
    private String role;
    @OneToMany
    private List<Order> orders = new ArrayList<>();

}
