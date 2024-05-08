package com.example.deliveryapplication.cartItems;

import com.example.deliveryapplication.menus.MenusEntity;
import com.example.deliveryapplication.shoppingCart.ShoppingCartEntity;
import com.example.deliveryapplication.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
public class CartItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCartEntity shoppingCart;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private MenusEntity menu;

    @Column(nullable = false)
    private int quantity;

    private int price;
}
