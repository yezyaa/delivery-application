package com.example.deliveryapplication.menus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenusController {
    private MenusService menusService;

    @Autowired
    public MenusController(MenusService menusService) {
        this.menusService = menusService;
    }

    // 메뉴 등록
    @PostMapping("/menus")
    public void saveMenuItem(@RequestBody Menus menu) {
        System.out.println("POST");
        menusService.saveMenu(menu);
    }

    // 메뉴 단일 조회
    @GetMapping("/menus/{id}")
    public Menus findMenu(@PathVariable("id") int id) {
        System.out.println("GET");
        System.out.println(id);
        return menusService.findMenu(id);
    }

    // 메뉴 전체 조회
    @GetMapping("/menus")
    public List<Menus> findMenuItems() {
        System.out.println("GET");
        return menusService.findMenus();
    }

    // 메뉴 수정
    @PutMapping("/menus/{id}")
    public void updateMenu(@PathVariable("id") int id, @RequestBody Menus menu) {
        System.out.println("UPDATE");
        menusService.updateMenu(id, menu);
    }

    // 메뉴 삭제
    @DeleteMapping("/menus/{id}")
    public void deleteMenu(@PathVariable("id") int id) {
        System.out.println("DELETE");
        menusService.deleteMenu(id);
    }
}
