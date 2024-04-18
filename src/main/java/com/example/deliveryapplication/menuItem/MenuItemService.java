package com.example.deliveryapplication.menuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class MenuItemService {
    MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    // 메뉴 개별 조회
    public MenuItem findMenuItem(int id) {
        return menuItemRepository.findMenuItem(id);
    }

    // 메뉴 전체 조회
    public List<MenuItem> findMenuItems() {
        return menuItemRepository.findMenuItems();
    }

    // 메뉴 등록
    public void saveMenuItem(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
    }

    // 메뉴 삭제
    public void deleteMenuItem(int id) {
        menuItemRepository.deleteMenuItem(id);
    }
}
