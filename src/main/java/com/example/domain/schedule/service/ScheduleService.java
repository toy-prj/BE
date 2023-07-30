package com.example.domain.schedule.service;

import com.example.domain.schedule.domain.Schedule;
import com.example.domain.schedule.domain.response.ScheduleListResponseDto;
import com.example.domain.schedule.repository.ScheduleRepository;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.global.exception.CustomException;
import com.example.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.global.exception.ErrorCode.SCHEDULE_NOT_FOUND;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public boolean deleteById(Long id) {
        scheduleRepository.deleteById(id);
        return true;
    }

    public List<ScheduleListResponseDto> findAllByMovieId(Long movieId) {
        List<ScheduleListResponseDto> collect = scheduleRepository.findAllByMovie_Id(movieId).stream()
                .map(ScheduleListResponseDto::new).collect(toList());
        return collect;
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule findById(Long id){
        return scheduleRepository.findById(id).orElseThrow(
                () -> new CustomException(SCHEDULE_NOT_FOUND.getMessage(), SCHEDULE_NOT_FOUND)
        );
    }
}
