package com.example.domain.schedule.controller;

import com.example.domain.schedule.domain.request.SaveScheduleRequestDto;
import com.example.domain.schedule.domain.response.ScheduleListResponseDto;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleFacadeService facadeService;

    @PostMapping
    public ResponseEntity<Long> saveSchedule(@Validated SaveScheduleRequestDto dto){
        return ResponseEntity.ok().body(facadeService.saveSchedule(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSchedule(@PathVariable Long id){
        return ResponseEntity.ok().body(scheduleService.deleteById(id));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<List<ScheduleListResponseDto>> getScheduleList(@PathVariable Long movieId){
        return ResponseEntity.ok().body(scheduleService.findAllByMovieId(movieId));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<List<SeatListResponseDto>> getSeatListByScheduleId(@PathVariable Long scheduleId){
        return ResponseEntity.ok().body(facadeService.getSeatListByScheduleId(scheduleId));
    }

}
