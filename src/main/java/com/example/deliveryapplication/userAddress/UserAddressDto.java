package com.example.deliveryapplication.userAddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressDto {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private int userId;             // 회원ID
    private String name;            // 주소지명
    private String address;         // 주소

    public static UserAddressDto fromEntity(UserAddressEntity entity) {
        UserAddressDto dto = new UserAddressDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        return dto;
    }
}
