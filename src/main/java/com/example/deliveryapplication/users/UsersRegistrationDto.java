package com.example.deliveryapplication.users;

import lombok.Getter;

@Getter
public class UsersRegistrationDto {
    private String name;                    // 이름
    private String nickname;                // 닉네임
    private String email;                   // 이메일
    private String phoneNumber;             // 전화번호
    private String password;                // 비밀번호
    private String confirmPassword;         // 비밀번호 확인
    private String role;                    // 권한(회원/사장/관리자)
}
