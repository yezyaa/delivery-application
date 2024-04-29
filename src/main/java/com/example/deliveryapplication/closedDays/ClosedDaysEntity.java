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

    private String day;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoresEntity store;
}