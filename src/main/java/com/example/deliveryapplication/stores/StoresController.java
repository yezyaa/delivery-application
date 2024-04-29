package com.example.deliveryapplication.stores;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoresController {
    private final StoresService storesService;

    // 식당 등록
    @PostMapping
    public void saveStore(@RequestBody StoresDto dto) {
        log.info("POST");
        storesService.saveStore(dto);
    }

    // 식당 전체 조회
    @GetMapping
    public List<StoresDto> findStores() {
        log.info("GET ALL");
        return storesService.findStores();
    }

    // 식당 단일 조회
    @GetMapping("/{id}")
    public StoresDto findStore(@PathVariable("id") int id) {
        log.info("GET Id = " + id);
        return storesService.findStore(id);
    }

    // 식당 수정
    @PutMapping("/{id}")
    public StoresDto updateStore(
            @PathVariable("id") int id,
            @RequestBody StoresDto dto
    ) {
        log.info("UPDATE Id = " + id);
        return storesService.updateStore(id, dto);
    }

    // 식당 삭제
    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable("id") int id) {
        log.info("Delete Id = " + id);
        storesService.deleteStore(id);
    }
}
