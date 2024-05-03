package com.example.deliveryapplication.userAddress;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/user-address")
public class UserAddressController {
    private final UserAddressService userAddressService;

    // 주소 등록
    @PostMapping
    public void saveUserAddress(
            @PathVariable("userId") int userId,
            @RequestBody UserAddressDto dto
    ) {
        log.info("POST User id = " + userId);
        userAddressService.saveUserAddress(userId, dto);
    }

    // 사용자별 주소 전체 조회
    @GetMapping
    public List<UserAddressDto> findUserAddressesByUserId(@PathVariable("userId") int userId) {
        log.info("GET ALL User id = " + userId);
        return userAddressService.findUserAddressesByUserId(userId);
    }

    // 사용자별 주소지명 수정
    @PatchMapping("/{id}/name")
    public UserAddressDto updateUserAddressNameByUserId(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id,
            @RequestBody UserAddressDto dto
    ) {
        log.info("UPDATE User id = " + userId);
        return userAddressService.updateUserAddressNameByUserId(userId, id, dto);
    }

    // 사용자별 주소 수정
    @PatchMapping("/{id}/address")
    public UserAddressDto updateUserAddressByUserId(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id,
            @RequestBody UserAddressDto dto
    ) {
        log.info("UPDATE User id = " + userId);
        return userAddressService.updateUserAddressByUserId(userId, id, dto);
    }
    
    // 사용자별 특정 주소 삭제
    @DeleteMapping("/{id}")
    public void deleteUserAddressByUserId(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("Delete Id = " + id);
        userAddressService.deleteUserAddressByUserId(id);
    }
}
