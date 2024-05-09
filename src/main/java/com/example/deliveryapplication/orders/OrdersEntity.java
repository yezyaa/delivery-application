package com.example.deliveryapplication.orders;

import com.example.deliveryapplication.orderItems.OrderItemsEntity;
import com.example.deliveryapplication.users.UsersEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @Column(name = "delivery_requests")
    private String deliveryRequests;

    @Column(name = "payment_method", length = 50, nullable = false)
    private String paymentMethod;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Builder.Default
    @Column(name = "order_status", length = 10, nullable = false)
    private String orderStatus = "대기";

    @Column(name = "delivery_status", length = 10)
    private String deliveryStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<OrderItemsEntity> orderItems = new ArrayList<>();
}
