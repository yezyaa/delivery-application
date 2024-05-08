package com.example.deliveryapplication.shoppingCart;

import com.example.deliveryapplication.cartItems.CartItemsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ShoppingCartDto {
    private int id;
    private int userId;                         // 회원ID
    private List<CartItemsDto> cartItems;       // 담은메뉴정보

    public static ShoppingCartDto fromEntity(ShoppingCartEntity entity) {
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setCartItems(entity.getCartItems().stream()
                .map(CartItemsDto::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }
}
