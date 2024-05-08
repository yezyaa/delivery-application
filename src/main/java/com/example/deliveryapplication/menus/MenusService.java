package com.example.deliveryapplication.menus;

import com.example.deliveryapplication.stores.SpringDataJPAStoresRepository;
import com.example.deliveryapplication.stores.StoresEntity;
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
    private final SpringDataJPAStoresRepository storesRepository;
    private final SpringDataJPAMenusRepository menusRepository;

    // 메뉴 등록
    public void saveMenu(int storeId, MenusDto dto) {
        StoresEntity storesEntity = storesRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴를 등록할 식당이 없습니다."));

        MenusEntity newMenus = MenusEntity.builder()
                .store(storesEntity)
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .status(dto.getStatus())
                .build();
        menusRepository.save(newMenus);
    }

    // 메뉴 전체 조회
    public List<MenusDto> findMenus() {
        List<MenusDto> menusDto = new ArrayList<>();
        for (MenusEntity menusEntity : menusRepository.findAll()) {
            menusDto.add(MenusDto.fromEntity(menusEntity));
        }
        return menusDto;
    }

    // 메뉴 단일 조회
    public MenusDto findMenu(int id) {
        Optional<MenusEntity> optionalMenus = menusRepository.findById(id);
        if (optionalMenus.isPresent())
            return MenusDto.fromEntity(optionalMenus.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // 메뉴 수정
    public MenusDto updateMenu(int id, MenusDto dto) {
        Optional<MenusEntity> optionalMenus = menusRepository.findById(id);
        if (optionalMenus.isPresent()) {
            MenusEntity menusEntity = optionalMenus.get();
            menusEntity.setName(dto.getName());
            menusEntity.setPrice(dto.getPrice());
            menusEntity.setDescription(dto.getDescription());
            menusEntity.setUpdatedAt(LocalDateTime.now());
            menusEntity.setStatus(dto.getStatus());
            return MenusDto.fromEntity(menusRepository.save(menusEntity));
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // 메뉴 삭제
    public void deleteMenu(int id) {
        if (menusRepository.existsById(id))
            menusRepository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
