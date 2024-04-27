package com.example.deliveryapplication.menus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/menus")
public class MenusController {
    private final MenusService menusService;

    // 메뉴 등록
    @PostMapping
    public void saveMenu(@RequestBody MenusDto dto) {
        log.info("POST");
        menusService.saveMenu(dto);
    }

    // 메뉴 전체 조회
    @GetMapping
    public List<MenusDto> findMenus() {
        log.info("GET All");
        return menusService.findMenus();
    }

    // 메뉴 단일 조회
    @GetMapping("/{id}")
    public MenusDto findMenu(@PathVariable("id") int id) {
        log.info("GET Id = " + id);
        return menusService.findMenu(id);
    }

    // 메뉴 수정
    @PutMapping("/{id}")
    public MenusDto updateMenu(
            @PathVariable("id") int id,
            @RequestBody MenusDto dto
    ) {
        log.info("UPDATE Id = " + id);
        return menusService.updateMenu(id, dto);
    }

    // 메뉴 삭제
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable("id") int id) {
        log.info("DELETE Id = " + id);
        menusService.deleteMenu(id);
    }
}
