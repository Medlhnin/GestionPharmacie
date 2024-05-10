package com.example.gestionpharmacie.Commande;

import com.example.gestionpharmacie.Dto.CommandeDto;
import com.example.gestionpharmacie.Medicament.Medicament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/commande")
public class CommandeController {
    private final CommandeService commandeService;
    @Autowired
    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }
    @PostMapping
    public ResponseEntity<Void> createCommande(@RequestBody CommandeDto commandeDto, UriComponentsBuilder ucb) {
        Commande savedCommande = commandeService.createCommande(commandeDto);
        URI locationOfCommande = ucb
                .path("commande/{id}")
                .buildAndExpand(savedCommande.getId())
                .toUri();
        return ResponseEntity.created(locationOfCommande).build();
    }
}
