package com.example.deliveryapplication.cartItems;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/cart-items")
public class CartItemsController {
    private final CartItemsService cartItemsService;

    // 회원별 장바구니 메뉴 담기
    @PostMapping
    public void saveMenuItems(
            @PathVariable("userId") int userId,
            @RequestBody CartItemsDto dto
    ) {
        log.info("POST User Id = " + userId);
        cartItemsService.saveMenuItems(userId, dto);
    }

    // 회원별 장바구니 메뉴 수량 수정
    @PatchMapping("/{id}")
    public CartItemsDto updateMenusQuantity(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id,
            @RequestBody CartItemsDto dto
    ) {
        log.info("PATCH Id = " + id + ", Quntity = " + dto.getQuantity());
        return cartItemsService.updateMenusQuantity(id, dto);
    }

    // 회원별 장바구니 특정 메뉴 삭제
    @DeleteMapping("/{id}")
    public void deleteMenuItem(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("Delete User Id = " + userId + ", Id = " + id);
        cartItemsService.deleteMenuItem(userId, id);
    }

    // 회원별 장바구니 전체 메뉴 삭제
    @DeleteMapping
    public void deleteAllMenuItems(
            @PathVariable("userId") int userId
    ) {
        log.info("Delete User Id = " + userId);
        cartItemsService.deleteAllMenuItems(userId);
    }
}
