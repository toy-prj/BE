package com.example.domain.schedule.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.service.SeatService;
import com.example.domain.theater.domain.Theater;
import com.example.domain.movie.service.MovieService;
import com.example.domain.theater.service.TheaterService;
import com.example.domain.schedule.domain.request.SaveScheduleRequestDto;
import com.example.domain.schedule.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleFacadeService {

    private final MovieService movieService;
    private final ScheduleService scheduleService;
    private final TheaterService theaterService;
    private final SeatService seatService;

    public Long saveSchedule(SaveScheduleRequestDto dto) {

        Movie movie = movieService.findById(dto.getMovieId());
        Theater theater = theaterService.findById(dto.getTheaterId());
        Schedule schedule = Schedule.of(dto.getTime(), movie, theater);
        Schedule savedSchedule = scheduleService.save(schedule);
        return savedSchedule.getId();
    }

    public List<SeatListResponseDto> getSeatListByScheduleId(Long scheduleId) {

        Schedule schedule = scheduleService.findById(scheduleId);
        Theater theater = schedule.getTheater();
        Long theaterId = theater.getId();
        List<SeatListResponseDto> seatList = seatService.findAllByTheaterId(theaterId);
        return seatList;
    }
}
