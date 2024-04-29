package com.example.deliveryapplication.closedDays;

import com.example.deliveryapplication.stores.SpringDataJPAStoresRepository;
import com.example.deliveryapplication.stores.StoresEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClosedDaysService {
    private final SpringDataJPAStoresRepository storesRepository;
    private final SpringDataJPAClosedDaysRepository closedDaysRepository;

    // 휴무일 등록
    public void saveClosedDays(int storeId, ClosedDaysDto dto) {
        StoresEntity storesEntity = storesRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "휴무일을 등록할 식당이 없습니다."));

        ClosedDaysEntity newClosedDay = ClosedDaysEntity.builder()
                    .store(storesEntity)
                    .day(dto.getDay())
                    .build();
        closedDaysRepository.save(newClosedDay);
    }

    // 식당별 휴무일 전체 조회
    public List<ClosedDaysDto> findClosedDaysByStoreId(int storeId) {
        List<ClosedDaysDto> closedDaysDtoList = new ArrayList<>();
        for (ClosedDaysEntity closedDaysEntity : closedDaysRepository.findByStoreId(storeId)) {
            closedDaysDtoList.add(ClosedDaysDto.fromEntity(closedDaysEntity));
        }
        return closedDaysDtoList;
    }

    // 식당별 휴무일 수정
    public ClosedDaysDto updateClosedDay(int storeId, int id, ClosedDaysDto dto) {
        Optional<StoresEntity> storesEntity = storesRepository.findById(storeId);
        if (storesEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "휴무일을 수정할 식당이 없습니다.");

        Optional<ClosedDaysEntity> optionalClosedDaysEntity = closedDaysRepository.findById(id);
        if (optionalClosedDaysEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "휴무일을 찾을 수 없습니다.");

        ClosedDaysEntity closedDaysEntity = optionalClosedDaysEntity.get();
        closedDaysEntity.setDay(dto.getDay());
        return ClosedDaysDto.fromEntity(closedDaysRepository.save(closedDaysEntity));
    }

    // 식당별 휴무일 전체 삭제
    public void deleteClosedDaysByStoreId(int storeId) {
        List<ClosedDaysEntity> closedDaysEntityList = closedDaysRepository.findByStoreId(storeId);
        if (closedDaysEntityList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "휴무일을 찾을 수 없습니다.");
        closedDaysRepository.deleteAll(closedDaysEntityList);
    }

    // 식당별 휴무일 단일 삭제
    public void deleteClosedDayByStoreId(int storeId, int id) {
        Optional<ClosedDaysEntity> optionalClosedDaysEntity = closedDaysRepository.findById(id);
        if (optionalClosedDaysEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "휴무일을 찾을 수 없습니다.");

        if (optionalClosedDaysEntity.get().getStore().getId() != storeId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 식당의 휴무일을 삭제할 수 없습니다.");
        }

        closedDaysRepository.deleteById(id);
    }
}
