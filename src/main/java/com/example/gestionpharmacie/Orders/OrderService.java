package com.example.gestionpharmacie.Orders;

import com.example.gestionpharmacie.Dto.OrderItemRequest;
import com.example.gestionpharmacie.Inventory.Inventory;
import com.example.gestionpharmacie.Inventory.InventoryRepository;
import com.example.gestionpharmacie.Medicaments.Medicament;
import com.example.gestionpharmacie.Medicaments.MedicamentRepository;
import com.example.gestionpharmacie.OrderItem.OrderItem;
import com.example.gestionpharmacie.Users.Utilisateur;
import com.example.gestionpharmacie.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MedicamentRepository medicamentRepository;
    private final InventoryRepository inventoryRepository;
    private final EmailService emailService;

    public Order placeOrder(String username, List<OrderItemRequest> items) {
        Utilisateur user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUtilisateur(user);
        double totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : items) {
            Medicament medicament = medicamentRepository.findById(itemRequest.getMedicamentId())
                    .orElseThrow(() -> new RuntimeException("Medicament not found"));
            totalPrice += itemRequest.getQuantity() * medicament.getPrice();

            // Récupération du stock associé au médicament
            try {
                Inventory inventory = inventoryRepository.findByMedicament(medicament)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory NOT FOUND."));
                inventory.retirerDuStock(itemRequest.getQuantity());
                inventory.setLastUpdated(LocalDateTime.now());
                inventoryRepository.save(inventory);
            }
            catch (RuntimeException e)
            {
                e.printStackTrace();
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMedicament(medicament);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItems.add(orderItem);
        }
        order.setTotalPrice(totalPrice);
        order.setItems(orderItems);

        // Génération du token unique
        String token = UUID.randomUUID().toString();
        order.setConfirmationToken(token);

        orderRepository.save(order);

        // Envoi de l'e-mail de confirmation
        emailService.sendOrderConfirmationEmail(user.getEmail(), token);
        return order;
    }

    public List<Order> getOrdersByUser(String username) {
        Utilisateur user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUtilisateur_Id(user.getId());
    }

}
