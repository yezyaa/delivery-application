package com.example.deliveryapplication.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminController {
    private final UsersService usersService;

    // 사용자 전제 조회
    @GetMapping
    public List<UsersDto> findUsers() {
        log.info("GET ALL");
        return usersService.findUsers();
    }

    // 사용자 단일 조회
    @GetMapping("/{id}")
    public UsersDto findUser(@PathVariable("id") int id) {
        log.info("GET Id = " + id);
        return usersService.findUser(id);
    }
}
