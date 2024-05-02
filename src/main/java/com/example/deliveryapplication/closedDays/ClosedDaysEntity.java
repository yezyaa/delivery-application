package com.example.deliveryapplication.closedDays;

import com.example.deliveryapplication.stores.StoresEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "closed_days")
public class ClosedDaysEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoresEntity store;

    @Column(length = 10, nullable = false)
    private String day;
}