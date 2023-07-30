package com.example.domain.schedule.domain.response;

import com.example.domain.schedule.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleListResponseDto {

    private Long id;
    private LocalDateTime time;
    private String name;
    private String genre;
    private LocalDate release;
    private String theater;
    private String location;

    public ScheduleListResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.time = schedule.getTime();
        this.name = schedule.getMovie().getName();
        this.genre = schedule.getMovie().getGenre();
        this.release = schedule.getMovie().getRelease();
        this.theater = schedule.getTheater().getName();
        this.location = schedule.getTheater().getLocation();

    }

}
