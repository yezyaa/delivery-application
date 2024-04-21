package com.example.deliveryapplication.menus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenusService {
    @Autowired
    private SpringDataJPAMenusRepository springDataJPAMenusRepository;

    // 메뉴 등록
    public void saveMenu(Menus menu) {
        springDataJPAMenusRepository.save(menu);
    }

    // 메뉴 단일 조회
    public Menus findMenu(int id) {
        return springDataJPAMenusRepository.findById(id).get();
    }

    // 메뉴 전체 조회
    public List<Menus> findMenus() {
        return springDataJPAMenusRepository.findAll();
    }

    // 메뉴 수정
    public Menus updateMenu(int id, Menus menu) {
        menu.setName(menu.getName());
        return springDataJPAMenusRepository.save(menu);
        // 미구현`
    }

    // 메뉴 삭제
    public void deleteMenu(int id) {
        springDataJPAMenusRepository.deleteById(id);
    }
}
