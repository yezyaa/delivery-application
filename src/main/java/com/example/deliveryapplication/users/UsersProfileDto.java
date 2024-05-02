package com.example.deliveryapplication.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsersProfileDto {
    private int id;
    private String name;                    // 이름
    private String nickname;                // 닉네임
    private String email;                   // 이메일
    private String phoneNumber;             // 전화번호
    private String role;                    // 권한(회원/사장/관리자)
    private LocalDateTime createdAt;        // 생성일시

    public static UsersProfileDto fromEntity(UsersEntity entity) {
        UsersProfileDto dto = new UsersProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setRole(entity.getRole());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
