package com.example.deliveryapplication.menus;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MenusDto {
    private int id;
    private int storeId;                // 식당ID
    private String name;                // 메뉴명
    private int price;                  // 메뉴가격
    private String description;         // 메뉴설명
    private LocalDateTime createdAt;    // 생성일시
    private LocalDateTime updatedAt;    // 수정일시
    private String status;              // 상태(판매중/준비중/품절)

    public static MenusDto fromEntity(MenusEntity entity) {
        MenusDto dto = new MenusDto();
        dto.setId(entity.getId());
        dto.setStoreId(entity.getStore().getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}