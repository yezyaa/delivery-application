package com.example.deliveryapplication.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}")
public class OrdersController {
    private final OrdersService ordersService;

    // 주문(결제)
    @PostMapping("/orders")
    public void saveOrder(
            @PathVariable("userId") int userId,
            @RequestBody OrdersDto dto
            ) {
        log.info("POST User Id = " + userId);
        ordersService.saveOrder(userId, dto);
    }

    // 회원별 주문 전체 조회 (주문 내역 조회)
    @GetMapping("/orders")
    public List<OrdersDto> findOrdersByUserId(@PathVariable("userId") int userId) {
        log.info("GET All User Id = " + userId);
        return ordersService.findOrdersByUserId(userId);
    }

    // 회원별 주문 단일 조회
    @GetMapping("/orders/{id}")
    public OrdersDto findOrderByUserId(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("GET User Id = " + userId + ", Order Id = " + id);
        return ordersService.findOrderByUserId(userId, id);
    }

    // 회원별 주문 취소
    @DeleteMapping("/orders/{id}")
    public void deleteOrderByUserId(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("DELETE User Id = " + userId + ", Order Id = " + id);
        ordersService.deleteOrderByUserId(userId, id);
    }

    // 사장별 특정 매장 주문 전체 조회
    @GetMapping("/stores/{storeId}/orders")
    public List<OrdersDto> findOrdersByStoreIdAndUserId(
            @PathVariable("userId") int userId,
            @PathVariable("storeId") int storeId
    ) {
        log.info("GET All User Id = " + userId + ", Store Id = " + storeId);
        return ordersService.findOrdersByStoreIdAndUserId(userId, storeId);
    }

    // 사장별 특정 매장 주문 단일 조회
    @GetMapping("/stores/{storeId}/orders/{id}")
    public OrdersDto findOrderByStoreIdAndUserId(
            @PathVariable("userId") int userId,
            @PathVariable("storeId") int storeId,
            @PathVariable("id") int id
    ) {
        log.info("GET User Id = " + userId + ", Store Id = " + storeId + ", Order Id = " + id);
        return ordersService.findOrderByStoreIdAndUserId(userId, storeId, id);
    }

    // 사장별 특정 매장 주문상태(수락/거절) 및 픽업상태(요청/픽업) 수정
    @PutMapping("/stores/{storeId}/orders/{id}")
    public OrdersDto updateOrderStatusByStoreIdAndUserId(
            @PathVariable("userId") int userId,
            @PathVariable("storeId") int storeId,
            @PathVariable("id") int id,
            @RequestBody OrdersDto dto
    ) {
        log.info("UPDATE User Id = " + userId + ", Store Id = " + storeId + ", Order Id = " + id);
        return ordersService.updateOrderStatusByStoreIdAndUserId(userId, storeId, id, dto);
    }
}
