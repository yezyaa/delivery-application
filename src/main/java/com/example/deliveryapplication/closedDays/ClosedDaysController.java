package com.example.deliveryapplication.closedDays;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stores/{storeId}/closed-days")
public class ClosedDaysController {
    private final ClosedDaysService closedDaysService;

    // 휴무일 등록
    @PostMapping
    public void saveClosedDays(
            @PathVariable("storeId") int storeId,
            @RequestBody ClosedDaysDto dto
            ) {
        log.info("POST Store id = " + storeId + ", Day = " + dto.getDay());
        closedDaysService.saveClosedDays(storeId, dto);
    }

    // 식당별 휴무일 전체 조회
    @GetMapping
    public List<ClosedDaysDto> findClosedDaysByStoreId(@PathVariable("storeId") int storeId) {
        log.info("GET ALL Store id = " + storeId);
        return closedDaysService.findClosedDaysByStoreId(storeId);
    }

    // 식당별 휴무일 수정
    @PatchMapping("/{id}")
    public ClosedDaysDto updateClosedDay(
            @PathVariable("storeId") int storeId,
            @PathVariable("id") int id,
            @RequestBody ClosedDaysDto dto
    ) {
        log.info("UPDATE Store id = " + storeId + ", Day = " + dto.getDay());
        return closedDaysService.updateClosedDay(storeId, id, dto);
    }

    // 식당별 휴무일 전체 삭제
    @DeleteMapping
    public void deleteClosedDaysByStoreId(@PathVariable("storeId") int storeId) {
        log.info("Delete Store id = " + storeId);
        closedDaysService.deleteClosedDaysByStoreId(storeId);
    }

    // 식당별 특정 휴무일 삭제
    @DeleteMapping("/{id}")
    public void deleteClosedDayByStoreId(
            @PathVariable("storeId") int storeId,
            @PathVariable("id") int id
    ) {
        log.info("Delete Id = " + id);
        closedDaysService.deleteClosedDayByStoreId(id);
    }
}
