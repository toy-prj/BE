package com.example.domain.schedule.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.theater.domain.Theater;
import com.example.domain.theater.repository.TheaterRepository;
import com.example.domain.schedule.domain.request.SaveScheduleRequestDto;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.domain.response.ScheduleListResponseDto;
import com.example.domain.movie.repository.MovieRepository;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
@Transactional
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleFacadeService facadeService;


    @Test
    @DisplayName("영화 스케줄을 등록 할 수 있다.")
    void addSchedule() {

        // given

        Long schedule1 = facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(1L)
                .theaterId(1L)
                .time(LocalDateTime.parse("2023-07-18T12:00")).build());

        Long schedule2 = facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(1L)
                .theaterId(3L)
                .time(LocalDateTime.parse("2023-07-18T18:00")).build());

        Long schedule3 = facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(2L)
                .theaterId(2L)
                .time(LocalDateTime.parse("2023-07-18T12:00")).build());

        Long schedule4 = facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(3L)
                .theaterId(4L)
                .time(LocalDateTime.parse("2023-07-18T18:00")).build());

        // when
        String name1 = scheduleService.findById(schedule1).getMovie().getName();
        String name2= scheduleService.findById(schedule2).getTheater().getName();
        String name3 = scheduleService.findById(schedule3).getMovie().getName();
        String name4 = scheduleService.findById(schedule4).getTheater().getName();

        // then
        assertEquals("도둑들", name1);
        assertEquals("강남3관", name2);
        assertEquals("FAST AND FURIOUS", name3);
        assertEquals("홍대1관", name4);

    }

    @Test
    @DisplayName("영화id 로 스케줄 리스트를 조회 할 수 있다.")
    void scheduleList() {
        // given
        long movieId = 4L;

        // when
        List<ScheduleListResponseDto> scheduleList = scheduleService.findAllByMovieId(movieId);

        // then
        assertEquals(4, scheduleList.size());
        assertEquals("타짜", scheduleList.get(0).getName());
    }

}