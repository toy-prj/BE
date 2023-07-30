package com.example.domain.seat.service;


import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Rollback
@Transactional
@SpringBootTest
class SeatFacadeServiceTest {
    
    @Autowired
    ScheduleFacadeService scheduleFacadeService;

    @Test
    @DisplayName("스케줄 id로 사용 가능한 좌석 리스트와 갯수를 확인 할 수 있어야 한다.")
    void getSeatListByScheduleId() {

        // given
        long scheduleId1 = 1L;


        // when
        List<SeatListResponseDto> list1 = scheduleFacadeService.getSeatListByScheduleId(scheduleId1);


        // then
        assertEquals(10, list1.size());
        assertEquals("A1", list1.get(0).getName());

    }

}