package com.example.gestionpharmacie.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private Long medicamentId;
    private double price;
    private int quantity;
}
