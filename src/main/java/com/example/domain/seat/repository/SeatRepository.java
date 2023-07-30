package com.example.domain.seat.repository;

import com.example.domain.schedule.domain.Schedule;
import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByName(String name);


    List<SeatListResponseDto> findAllByTheaterId(Long theaterId);

}
