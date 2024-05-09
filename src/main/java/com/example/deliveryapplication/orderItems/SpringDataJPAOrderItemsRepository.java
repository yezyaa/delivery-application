package com.example.deliveryapplication.orderItems;

import com.example.deliveryapplication.orders.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJPAOrderItemsRepository extends JpaRepository<OrderItemsEntity, Integer> {
    List<OrderItemsEntity> findByOrder(OrdersEntity newOrders);
}
