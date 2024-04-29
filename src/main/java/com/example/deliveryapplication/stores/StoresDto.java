package com.example.deliveryapplication.stores;

import com.example.deliveryapplication.closedDays.ClosedDaysDto;
import com.example.deliveryapplication.menus.MenusDto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class StoresDto {
    private int id;
    private String name;                        // 상호명
    private String address;                     // 주소
    private String tel;                         // 전화번호
    private String info;                        // 소개글
    private LocalTime operatingStartTime;       // 운영시작시간
    private LocalTime operatingEndTime;         // 운영종료시간
    private int minOrderPrice;                  // 최소주문금액
    private int deliveryTip;                    // 배달팁
    private LocalDateTime createdAt;            // 생성일시
    private LocalDateTime updatedAt;            // 수정일시
    
    private List<String> closedDaysList;        // 휴무일

    public static StoresDto fromEntity(StoresEntity entity) {
        StoresDto dto = new StoresDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setTel(entity.getTel());
        dto.setInfo(entity.getInfo());
        dto.setOperatingStartTime(entity.getOperatingStartTime());
        dto.setOperatingEndTime(entity.getOperatingEndTime());
        dto.setMinOrderPrice(entity.getMinOrderPrice());
        dto.setDeliveryTip(entity.getDeliveryTip());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}