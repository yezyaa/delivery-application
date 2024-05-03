package com.example.deliveryapplication.users;

import com.example.deliveryapplication.userAddress.UserAddressInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UsersDto {
    private int id;
    private String name;                                // 이름
    private String nickname;                            // 닉네임
    private String email;                               // 이메일
    private String phoneNumber;                         // 전화번호
    private List<UserAddressInfoDto> userAddress;       // 회원주소목록
    private String password;                            // 비밀번호
    private String role;                                // 권한(고객/사장/관리자)
    private LocalDateTime createdAt;                    // 생성일시
    private LocalDateTime updatedAt;                    // 수정일시
    private String status;                              // 상태(일반/탈퇴/휴먼/블랙리스트)

    public static UsersDto fromEntity(UsersEntity entity) {
        UsersDto dto = new UsersDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        if (entity.getUserAddresses() != null) {
            dto.setUserAddress(entity.getUserAddresses().stream()
                    .map(UserAddressInfoDto::returnAddressInfo)
                    .collect(Collectors.toList()));
        }
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
