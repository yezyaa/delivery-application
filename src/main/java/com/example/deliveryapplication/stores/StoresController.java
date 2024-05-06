package com.example.deliveryapplication.stores;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/stores")
public class StoresController {
    private final StoresService storesService;

    // 사장별 식당 등록
    @PostMapping
    public void saveStore(
            @PathVariable("userId") int userId,
            @RequestBody StoresDto dto
    ) {
        log.info("POST User Id = " + userId);
        storesService.saveStore(userId, dto);
    }

    // 사장별 식당 전체 조회
    @GetMapping
    public List<StoresDto> findStores(@PathVariable("userId") int userId) {
        log.info("GET ALL User Id = " + userId);
        return storesService.findStores(userId);
    }

    // 사장별 식당 단일 조회
    @GetMapping("/{id}")
    public StoresDto findStore(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("GET User Id = " + userId + ", Id = " + id);
        return storesService.findStore(userId, id);
    }

    // 사장별 특정 식당 수정
    @PutMapping("/{id}")
    public StoresDto updateStore(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id,
            @RequestBody StoresDto dto
    ) {
        log.info("Update User Id = " + userId + ", Id = " + id);
        return storesService.updateStore(userId, id, dto);
    }

    // 사장별 특정 식당 삭제
    @DeleteMapping("/{id}")
    public void deleteStore(
            @PathVariable("userId") int userId,
            @PathVariable("id") int id
    ) {
        log.info("Delete User Id = " + userId + ", Id = " + id);
        storesService.deleteStore(userId, id);
    }
}
