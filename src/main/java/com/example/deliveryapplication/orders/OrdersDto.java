package com.example.deliveryapplication.orders;

import com.example.deliveryapplication.orderItems.OrderItemsDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrdersDto {
    private int id;
    private int userId;                         // 회원ID
    private List<OrderItemsDto> orderItems;     // 주문한 메뉴정보
    private String deliveryRequests;            // 요청사항
    private String paymentMethod;               // 결제수단
    private int totalAmount;                    // 최종주문금액
    private String orderStatus;                 // 주문상태
    private String deliveryStatus;              // 배달상태
    private LocalDateTime createdAt;            // 생성일시
    private LocalDateTime updatedAt;            // 수정일시

    public static OrdersDto fromEntity(OrdersEntity entity) {
        OrdersDto dto = new OrdersDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setOrderItems(entity.getOrderItems().stream()
                .map(OrderItemsDto::fromEntity)
                .collect(Collectors.toList()));
        dto.setDeliveryRequests(entity.getDeliveryRequests());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setDeliveryStatus(entity.getDeliveryStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
