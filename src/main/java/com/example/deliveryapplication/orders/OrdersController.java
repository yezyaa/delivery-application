package com.example.deliveryapplication.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("users/{userId}/orders")
public class OrdersController {
    private final OrdersService ordersService;

    // 주문(결제)
    @PostMapping
    public void saveOrder(
            @PathVariable("userId") int userId,
            @RequestBody OrdersDto dto
            ) {
        log.info("POST User Id = " + userId);
        ordersService.saveOrder(userId, dto);
    }
}
