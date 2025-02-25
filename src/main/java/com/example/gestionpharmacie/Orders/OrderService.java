package com.example.gestionpharmacie.Orders;

import com.example.gestionpharmacie.Dto.OrderItemRequest;
import com.example.gestionpharmacie.Inventory.Inventory;
import com.example.gestionpharmacie.Inventory.InventoryRepository;
import com.example.gestionpharmacie.Medicaments.Medicament;
import com.example.gestionpharmacie.Medicaments.MedicamentRepository;
import com.example.gestionpharmacie.OrderItem.OrderItem;
import com.example.gestionpharmacie.Users.Utilisateur;
import com.example.gestionpharmacie.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicamentRepository medicamentRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    public Order placeOrder(Long userId, List<OrderItemRequest> items) {
        Utilisateur user = userRepository.findById(userId)
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
                Inventory inventory = inventoryRepository.findByMedicament(medicament);
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
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) {
        Utilisateur user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUtilisateur_Id(userId);
    }

}
