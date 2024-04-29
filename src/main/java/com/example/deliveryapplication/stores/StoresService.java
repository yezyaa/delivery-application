package com.example.deliveryapplication.stores;

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
@RequiredArgsConstructor
@Service
public class StoresService {
    private final SpringDataJPAStoresRepository springDataJPAStoresRepository;

    // 식당 등록
    public void saveStore(StoresDto dto) {
        StoresEntity newStore = StoresEntity.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .tel(dto.getTel())
                .info(dto.getInfo())
                .operatingStartTime(dto.getOperatingStartTime())
                .operatingEndTime(dto.getOperatingEndTime())
                .minOrderPrice(dto.getMinOrderPrice())
                .deliveryTip(dto.getDeliveryTip())
                .createdAt(LocalDateTime.now())
                .build();
        springDataJPAStoresRepository.save(newStore);
    }

    // 식당 전체 조회
    public List<StoresDto> findStores() {
        List<StoresDto> storesDtoList = new ArrayList<>();
        for (StoresEntity storesEntity : springDataJPAStoresRepository.findAll()) {
            storesDtoList.add(StoresDto.fromEntity(storesEntity));
        }
        return storesDtoList;
    }

    // 식당 단일 조회
    public StoresDto findStore(int id) {
        Optional<StoresEntity> optionalStoresEntity = springDataJPAStoresRepository.findById(id);
        if (optionalStoresEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다.");
        return StoresDto.fromEntity(optionalStoresEntity.get());
    }

    // 식당 수정
    public StoresDto updateStore(int id, StoresDto dto) {
        Optional<StoresEntity> optionalStoresEntity = springDataJPAStoresRepository.findById(id);

        if (optionalStoresEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다.");

        StoresEntity storesEntity = optionalStoresEntity.get();
        storesEntity.setName(dto.getName());
        storesEntity.setAddress(dto.getAddress());
        storesEntity.setTel(dto.getTel());
        storesEntity.setInfo(dto.getInfo());
        storesEntity.setOperatingStartTime(dto.getOperatingStartTime());
        storesEntity.setOperatingEndTime(dto.getOperatingEndTime());
        storesEntity.setMinOrderPrice(dto.getMinOrderPrice());
        storesEntity.setDeliveryTip(dto.getDeliveryTip());
        storesEntity.setUpdatedAt(LocalDateTime.now());
        return StoresDto.fromEntity(springDataJPAStoresRepository.save(storesEntity));
    }

    // 식당 삭제
    public void deleteStore(int id) {
        if (springDataJPAStoresRepository.existsById(id))
            springDataJPAStoresRepository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다.");
    }
}