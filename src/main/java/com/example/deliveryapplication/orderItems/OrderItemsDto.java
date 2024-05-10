package com.example.deliveryapplication.orderItems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsDto {
    private int id;
    private int orderId;        // 주문ID
    private int menuId;         // 메뉴ID
    private String menuName;    // 메뉴이름
    private int quantity;       // 수량
    private int price;          // 가격

    public static OrderItemsDto fromEntity(OrderItemsEntity entity) {
        OrderItemsDto dto = new OrderItemsDto();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrder().getId());
        dto.setMenuId(entity.getMenu().getId());
        dto.setMenuName(entity.getMenu().getName());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
