package com.example.deliveryapplication.orderItems;

import com.example.deliveryapplication.menus.MenusEntity;
import com.example.deliveryapplication.orders.OrdersEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrdersEntity order;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private MenusEntity menu;

    private int quantity;
    private int price;
}
