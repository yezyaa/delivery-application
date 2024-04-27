package com.example.deliveryapplication.menus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenusService {
    private final SpringDataJPAMenusRepository springDataJPAMenusRepository;

    // 메뉴 등록
    public void saveMenu(MenusDto dto) {
        MenusEntity newMenus = MenusEntity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .status(dto.getStatus())
                .build();
        springDataJPAMenusRepository.save(newMenus);
    }

    // 메뉴 전체 조회
    public List<MenusDto> findMenus() {
        List<MenusDto> menusDtoList = new ArrayList<>();
        for (MenusEntity menusEntity : springDataJPAMenusRepository.findAll()) {
            menusDtoList.add(MenusDto.fromEntity(menusEntity));
        }
        return menusDtoList;
    }

    // 메뉴 단일 조회
    public MenusDto findMenu(int id) {
        Optional<MenusEntity> optionalMenusEntity = springDataJPAMenusRepository.findById(id);
        if (optionalMenusEntity.isPresent())
            return MenusDto.fromEntity(optionalMenusEntity.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // 메뉴 수정
    public MenusDto updateMenu(int id, MenusDto dto) {
        Optional<MenusEntity> optionalMenusEntity = springDataJPAMenusRepository.findById(id);
        if (optionalMenusEntity.isPresent()) {
            MenusEntity menusEntity = optionalMenusEntity.get();
            menusEntity.setName(dto.getName());
            menusEntity.setPrice(dto.getPrice());
            menusEntity.setDescription(dto.getDescription());
            menusEntity.setUpdatedAt(LocalDateTime.now());
            menusEntity.setStatus(dto.getStatus());
            return MenusDto.fromEntity(springDataJPAMenusRepository.save(menusEntity));
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // 메뉴 삭제
    public void deleteMenu(int id) {
        if (springDataJPAMenusRepository.existsById(id))
            springDataJPAMenusRepository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
