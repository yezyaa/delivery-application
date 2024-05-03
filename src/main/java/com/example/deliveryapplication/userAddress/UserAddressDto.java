package com.example.deliveryapplication.userAddress;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "userId", "name", "address" })
public class UserAddressDto extends UserAddressInfoDto {
    private int id;
    private int userId;             // 회원ID

    public static UserAddressDto fromEntity(UserAddressEntity entity) {
        UserAddressDto dto = new UserAddressDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        return dto;
    }
}
