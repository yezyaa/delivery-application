package com.example.deliveryapplication.users;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
public class UsersPasswordDto {
    private String currentPassword;         // 현재 비밀번호
    private String updatePassword;          // 변경할 비밀번호
    private String confirmPassword;         // 변경할 비밀번호 확인

    public static UsersPasswordDto fromEntity(UsersEntity entity) {
        UsersPasswordDto dto = new UsersPasswordDto();
        dto.setCurrentPassword(entity.getPassword());
        log.info("비밀번호가 변경되었습니다.");
        return dto;
    }
}
