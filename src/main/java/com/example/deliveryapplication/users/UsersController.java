package com.example.deliveryapplication.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    
    // 회원가입
    @PostMapping
    public void saveUser(@RequestBody UsersRegistrationDto dto) {
        log.info("POST");
        usersService.saveUser(dto);
    }

    // 사용자 본인 정보 조회
    @GetMapping("/{id}/my-profile")
    public UsersProfileDto findMyProfile(@PathVariable("id") int id) {
        return usersService.findMyProfile(id);
    }

    // 사용자 본인 닉네임 수정
    @PatchMapping("/{id}/nickname")
    public UsersProfileDto updateNickname(
            @PathVariable("id") int id,
            @RequestBody UsersProfileDto dto
    ) {
        log.info("UPDATE Id = " + id);
        return usersService.updateNickname(id, dto);
    }
    
    // 사용자 본인 이메일
    @PatchMapping("/{id}/email")
    public UsersProfileDto updateEmail(
            @PathVariable("id") int id,
            @RequestBody UsersProfileDto dto
    ) {
        log.info("UPDATE Id = " + id);
        return usersService.updateEmail(id, dto);
    }
    
    // 사용자 본인 전화번호 수정
    @PatchMapping("/{id}/phone-number")
    public UsersProfileDto updatePhoneNumber(
            @PathVariable("id") int id,
            @RequestBody UsersProfileDto dto
    ) {
        log.info("UPDATE Id = " + id);
        return usersService.updatePhoneNumber(id, dto);
    }
    
    // 사용자 본인 비밀번호 수정
    @PutMapping("/{id}/password")
    public UsersPasswordDto updatePassword(
            @PathVariable("id") int id,
            @RequestBody UsersPasswordDto dto
    ) {
        log.info("UPDATE Id = " + id);
        return usersService.updatePassword(id, dto);
    }

    // 사용자 본인 탈퇴
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        log.info("Delete Id = " + id);
        usersService.deleteUser(id);
    }
}
