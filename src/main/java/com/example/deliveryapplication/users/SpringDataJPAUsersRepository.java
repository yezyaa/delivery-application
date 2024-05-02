package com.example.deliveryapplication.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPAUsersRepository extends JpaRepository<UsersEntity, Integer> {
}
