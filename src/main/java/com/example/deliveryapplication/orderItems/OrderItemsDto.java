package com.example.deliveryapplication.orderItems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsDto {
    private int id;
    private int shoppingCartId;         // 장바구니ID
    private int menuId;                 // 메뉴ID
    private String menuName;            // 메뉴이름
    private int quantity;               // 수량
    private int price;                  // 가격
}
