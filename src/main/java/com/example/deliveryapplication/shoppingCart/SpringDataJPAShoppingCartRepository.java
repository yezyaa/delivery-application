package com.example.deliveryapplication.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJPAShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Integer> {
    Optional<ShoppingCartEntity> findByUserId(int userId);
}
