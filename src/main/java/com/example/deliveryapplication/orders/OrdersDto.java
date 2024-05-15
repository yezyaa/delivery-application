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
    private int storeId;                        // 식당ID
    private List<OrderItemsDto> orderItems;     // 주문한 메뉴정보
    private String deliveryRequests;            // 요청사항
    private String paymentMethod;               // 결제수단
    private int totalAmount;                    // 최종주문금액
    private String orderStatus;                 // 주문상태(대기/수락/거절/취소)
    private String pickupStatus;                // 픽업상태(대기/요청/픽업)
    private String deliveryStatus;              // 배달상태(준비중/배달중/배달완료)
    private LocalDateTime createdAt;            // 생성일시
    private LocalDateTime updatedAt;            // 수정일시

    public static OrdersDto fromEntity(OrdersEntity entity) {
        OrdersDto dto = new OrdersDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setStoreId(entity.getStore().getId());
        dto.setOrderItems(entity.getOrderItems().stream()
                .map(OrderItemsDto::fromEntity)
                .collect(Collectors.toList()));
        dto.setDeliveryRequests(entity.getDeliveryRequests());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setTotalAmount(entity.getTotalAmount());
        if ((entity.getOrderStatus().equals("대기") && entity.getPickupStatus().equals("대기")) ||
            (entity.getOrderStatus().equals("수락") && entity.getPickupStatus().equals("대기")))
            entity.setDeliveryStatus("대기");
        else if (entity.getOrderStatus().equals("수락") && entity.getPickupStatus().equals("요청"))
            entity.setDeliveryStatus("준비중");
        else if (entity.getOrderStatus().equals("수락") && entity.getPickupStatus().equals("픽업"))
            entity.setDeliveryStatus("배달중");
        else entity.setDeliveryStatus("대기");
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setPickupStatus(entity.getPickupStatus());
        dto.setDeliveryStatus(entity.getDeliveryStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
