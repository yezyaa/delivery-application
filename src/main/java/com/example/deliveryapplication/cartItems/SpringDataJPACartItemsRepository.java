package com.example.deliveryapplication.cartItems;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJPACartItemsRepository extends JpaRepository<CartItemsEntity, Integer> {
    Optional<CartItemsEntity> findByShoppingCartIdAndMenuId(int shoppingCartId, int menuId);
    void deleteAllByShoppingCartId(int shoppingCartId);
}
