package com.example.domain.theater.controller;

import com.example.domain.seat.domain.request.SaveSeatRequestDto;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.service.SeatService;
import com.example.domain.theater.domain.request.SaveTheaterRequestDto;
import com.example.domain.theater.domain.request.UpdateTheaterRequestDto;
import com.example.domain.theater.domain.response.TheaterListResponseDto;
import com.example.domain.theater.service.TheaterService;
import com.example.domain.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theaters")
public class TheaterController {

    private final SeatService seatService;
    private final TheaterService theaterService;

    @GetMapping
    public ResponseEntity<List<TheaterListResponseDto>> getTheaterList(){
        return ResponseEntity.ok().body(theaterService.getList());
    }

    @PostMapping
    public ResponseEntity<Boolean> saveTheater(@Validated SaveTheaterRequestDto dto){
        return ResponseEntity.ok().body(theaterService.save(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> updateTheater(@PathVariable Long id, @Validated UpdateTheaterRequestDto dto){
        return ResponseEntity.ok().body(theaterService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTheater(@PathVariable Long id){
        return ResponseEntity.ok().body(theaterService.deleteById(id));
    }


    @GetMapping("/seats")
    public ResponseEntity<List<SeatListResponseDto>> getSeatList() {
        return ResponseEntity.ok().body(seatService.findAll());
    }

    @PostMapping("/seats")
    public ResponseEntity<Boolean> saveSeats(@Validated SaveSeatRequestDto dto){
        return ResponseEntity.ok().body(seatService.save(dto));
    }

}
