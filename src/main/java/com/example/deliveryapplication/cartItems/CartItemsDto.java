package com.example.deliveryapplication.cartItems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemsDto {
    private int id;
    private int shoppingCartId;         // 장바구니ID
    private int menuId;                 // 메뉴ID
    private String menuName;            // 메뉴이름
    private int quantity;               // 수량
    private int price;                  // 가격

    public static CartItemsDto fromEntity(CartItemsEntity entity) {
        CartItemsDto dto = new CartItemsDto();
        dto.setId(entity.getId());
        dto.setShoppingCartId(entity.getShoppingCart().getId());
        dto.setMenuId(entity.getMenu().getId());
        dto.setMenuName(entity.getMenu().getName());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
