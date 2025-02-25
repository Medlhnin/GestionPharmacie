package com.example.gestionpharmacie.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUtilisateur_Username(String username);

    List<Order> findByUtilisateur_Id(Long id);
}
