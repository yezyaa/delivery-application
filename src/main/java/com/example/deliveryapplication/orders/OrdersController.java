package com.example.deliveryapplication.orders;

import com.example.deliveryapplication.stores.StoresDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrdersController {
    private final OrdersService ordersService;

    // 주문(결제)
    @PostMapping("/users/{userId}/orders")
    public void saveOrder(
            @PathVariable("userId") int userId,
            @RequestBody OrdersDto dto
            ) {
        log.info("POST User Id = " + userId);
        ordersService.saveOrder(userId, dto);
    }

    // 회원별 주문 전체 조회 (주문 내역 조회)
    @GetMapping("/users/{userId}/orders")
    public List<OrdersDto> findOrdersByUserId(@PathVariable("userId") int userId) {
        log.info("GET All User Id = " + userId);
        return ordersService.findOrdersByUserId(userId);
    }

    // 회원별 주문 단일 조회
    @GetMapping("/users/{userId}/orders/{id}")
    public OrdersDto findOrderByUserId(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("GET User Id = " + userId + ", Order Id = " + id);
        return ordersService.findOrderByUserId(userId, id);
    }

    // 회원별 주문 취소
    @DeleteMapping("/users/{userId}/orders/{id}")
    public void deleteOrderByUserId(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info(("DELETE User Id = " + userId + ", Order Id = " + id));
        ordersService.deleteOrderByUserId(userId, id);
    }
}
