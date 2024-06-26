package com.example.deliveryapplication.stores;

import com.example.deliveryapplication.closedDays.ClosedDaysEntity;
import com.example.deliveryapplication.menus.MenusEntity;
import com.example.deliveryapplication.orders.OrdersEntity;
import com.example.deliveryapplication.userAddress.UserAddressEntity;
import com.example.deliveryapplication.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stores")
public class StoresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(length = 20, nullable = false)
    private String tel;

    @Column(columnDefinition = "TEXT")
    private String info;

    @Column(name = "operating_start_time", nullable = false)
    private LocalTime operatingStartTime;

    @Column(name = "operating_end_time", nullable = false)
    private LocalTime operatingEndTime;

    @Column(name = "min_order_price", nullable = false)
    private int minOrderPrice;

    @Column(name = "delivery_tip", nullable = false)
    private int deliveryTip;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<ClosedDaysEntity> closedDays = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<MenusEntity> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<OrdersEntity> orders = new ArrayList<>();
}