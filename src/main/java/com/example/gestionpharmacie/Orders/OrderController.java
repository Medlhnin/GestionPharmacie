package com.example.gestionpharmacie.Orders;

import com.example.gestionpharmacie.Config.JwtAuthFilter;
import com.example.gestionpharmacie.Dto.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping()
    public ResponseEntity<Order> placeOrder(@RequestHeader("X-User") String username, @RequestBody List<OrderItemRequest> items) {
        logger.info("📌 Commande passée par l'utilisateur : {}", username);
        Order order = orderService.placeOrder(username, items);
        return ResponseEntity.ok(order);
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getOrdersByUser(@RequestHeader("X-User") String username) {
        return ResponseEntity.ok(orderService.getOrdersByUser(username));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmOrder(@RequestParam String token) {
        Order order = orderRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée"));

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            return ResponseEntity.badRequest().body("Commande déjà confirmée ou annulée.");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationToken(null);
        orderRepository.save(order);

        return ResponseEntity.ok("Commande confirmée avec succès !");
    }
}
