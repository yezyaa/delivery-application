package com.example.deliveryapplication.orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPAOrdersRepository extends JpaRepository<OrdersEntity, Integer> {
}
