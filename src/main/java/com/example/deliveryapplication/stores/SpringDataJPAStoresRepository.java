package com.example.deliveryapplication.stores;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJPAStoresRepository extends JpaRepository<StoresEntity, Integer> {
    List<StoresEntity> findByUserId(int userId);
}
