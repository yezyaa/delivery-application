package com.example.deliveryapplication.closedDays;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClosedDaysDto {
    private int id;
    private int storeId;    // 식당ID
    private String day;     // 휴무일

    public static ClosedDaysDto fromEntity(ClosedDaysEntity entity) {
        ClosedDaysDto dto = new ClosedDaysDto();
        dto.setId(entity.getId());
        dto.setStoreId(entity.getStore().getId());
        dto.setDay(entity.getDay());
        return dto;
    }
}