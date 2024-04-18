package com.example.deliveryapplication.menuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemRepository {
    private Map<Integer, MenuItem> db = new HashMap<>();
    private int id = 1;

    // 개별 메뉴 조회
    public MenuItem findMenuItem(int idx) {
        return db.get(idx);
    }

    // 전체 메뉴 조회
    public List<MenuItem> findMenuItems() {
        return new ArrayList<>(db.values());
    }

    // 메뉴 등록
    public void save(MenuItem menuItem) {
        System.out.println(menuItem.getName());
        db.put(id++, menuItem);
    }

    // 메뉴 삭제
    public void deleteMenuItem(int id) {
        db.remove(id);
    }
}
