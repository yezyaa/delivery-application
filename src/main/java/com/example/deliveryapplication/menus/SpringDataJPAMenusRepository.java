package com.example.deliveryapplication.menus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPAMenusRepository extends JpaRepository<Menus, Integer> /* <엔티티명, ID Type> */ {
}
