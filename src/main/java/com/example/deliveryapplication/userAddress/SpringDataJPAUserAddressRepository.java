package com.example.deliveryapplication.userAddress;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJPAUserAddressRepository extends JpaRepository<UserAddressEntity, Integer> {
    List<UserAddressEntity> findByUserId(int userId);
}
