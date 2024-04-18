package com.example.deliveryapplication.menuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuItemController {
    private MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // 메뉴 개별 조회
    @GetMapping("/menuItems/{id}")
    public MenuItem findMenuItem(@PathVariable("id") int id) {
        System.out.println("GET");
        System.out.println(id);
        return menuItemService.findMenuItem(id);
    }

    // 메뉴 전체 조회
    @GetMapping("/menuItems")
    public List<MenuItem> findMenuItems() {
        System.out.println("GET");
        return menuItemService.findMenuItems();
    }

    // 메뉴 등록
    @PostMapping("/menuItems")
    public void saveMenuItem(@RequestBody MenuItem menuItem) {
        System.out.println("POST");
        menuItemService.saveMenuItem(menuItem);
    }

    // 메뉴 삭제
    @DeleteMapping("/menuItems/{id}")
    public void deleteMenuItem(@PathVariable("id") int id) {
        System.out.println("DELETE");
        menuItemService.deleteMenuItem(id);
    }
}
