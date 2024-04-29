package com.example.deliveryapplication.closedDays;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJPAClosedDaysRepository extends JpaRepository<ClosedDaysEntity, Integer> {
    List<ClosedDaysEntity> findByStoreId(int storeId);
}
