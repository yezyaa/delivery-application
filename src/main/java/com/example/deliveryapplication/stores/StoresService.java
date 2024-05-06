package com.example.deliveryapplication.stores;

import com.example.deliveryapplication.closedDays.ClosedDaysService;
import com.example.deliveryapplication.users.SpringDataJPAUsersRepository;
import com.example.deliveryapplication.users.UsersEntity;
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
    private final SpringDataJPAUsersRepository usersRepository;
    private final SpringDataJPAStoresRepository storesRepository;

    // 사장별 식당 등록
    public void saveStore(int userId, StoresDto dto) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (!usersEntity.getRole().equals("사장")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "식당을 등록할 권한이 없습니다.");
        };

        StoresEntity newStore = StoresEntity.builder()
                .user(usersEntity)
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
        storesRepository.save(newStore);
    }

    // 사장별 식당 전체 조회
    public List<StoresDto> findStores(int userId) {
        List<StoresDto> storesDtoList = new ArrayList<>();
        for (StoresEntity storesEntity : storesRepository.findByUserId(userId)) {
            storesDtoList.add(StoresDto.fromEntity(storesEntity));
        }
        return storesDtoList;
    }

    // 사장별 식당 단일 조회
    public StoresDto findStore(int userId, int id) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Optional<StoresEntity> optionalStoresEntity = storesRepository.findById(id);
        if (optionalStoresEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다.");

        return StoresDto.fromEntity(optionalStoresEntity.get());
    }

    // 사장별 특정 식당 수정
    public StoresDto updateStore(int userId, int id, StoresDto dto) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Optional<StoresEntity> optionalStoresEntity = storesRepository.findById(id);
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
        return StoresDto.fromEntity(storesRepository.save(storesEntity));
    }

    // 사장별 특정 식당 삭제
    public void deleteStore(int userId, int id) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Optional<StoresEntity> optionalStoresEntity = storesRepository.findById(id);
        if (optionalStoresEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다.");
        storesRepository.deleteById(id);
    }
}