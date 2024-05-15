package com.example.deliveryapplication.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJPAOrdersRepository extends JpaRepository<OrdersEntity, Integer> {
    List<OrdersEntity> findByUserId(int userId);

    List<OrdersEntity> findByStoreId(int storeId);
}
