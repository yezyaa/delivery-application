package com.example.deliveryapplication.userAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressInfoDto {
    private String name;            // 주소지명
    private String address;         // 주소

    public static UserAddressInfoDto returnAddressInfo(UserAddressEntity entity) {
        UserAddressInfoDto dto = new UserAddressInfoDto();
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        return dto;
    }
}