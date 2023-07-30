package com.example.domain.seat.controller;

import com.example.domain.seat.domain.request.SaveSeatRequestDto;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatListResponseDto>> getSeatList() {
        return ResponseEntity.ok().body(seatService.findAll());
    }

    @PostMapping
    public ResponseEntity<Boolean> saveSeats(@Validated SaveSeatRequestDto dto) {
        return ResponseEntity.ok().body(seatService.save(dto));
    }




}
