package com.example.gestionpharmacie.Orders;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(String to, String token) {
        String confirmationUrl = "http://localhost:8899/api/orders/confirm?token=" + token;
        String subject = "Confirmez votre commande";
        String message = "Cliquez sur le lien pour confirmer votre commande : " + confirmationUrl;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);

        mailSender.send(mail);
    }
}
