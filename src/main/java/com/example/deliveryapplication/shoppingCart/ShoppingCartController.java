package com.example.deliveryapplication.shoppingCart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("users/{userId}/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    // 회원별 장바구니 조회
    @GetMapping("/{id}")
    public ShoppingCartDto findShoppingCart(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("GET User Id = " + userId);
        return shoppingCartService.findShoppingCart(userId, id);
    }
}
