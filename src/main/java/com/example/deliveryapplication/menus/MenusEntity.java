package com.example.deliveryapplication.menus;

import com.example.deliveryapplication.cartItems.CartItemsEntity;
import com.example.deliveryapplication.orderItems.OrderItemsEntity;
import com.example.deliveryapplication.stores.StoresEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menus")
public class MenusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoresEntity store;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 10)
    private String status;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<CartItemsEntity> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<OrderItemsEntity> orderItems = new ArrayList<>();
}