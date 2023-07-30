package com.example.domain.schedule.repository;

import com.example.domain.schedule.domain.Schedule;
import com.example.domain.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    List<Schedule> findAllByMovie_Id(Long movieId);

    List<Seat> findAllByTheater_Id(Long theaterId);
}
