package com.example.domain.seat.domain.response;

import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.Sold;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatListResponseDto {

    private String name;
    private int seatCount;
    private Sold status;
    private Long id;

    public SeatListResponseDto(Seat seat) {
        this.name = seat.getName();
        this.seatCount = seat.getName().length();
        this.status = seat.getStatus();
        this.id = seat.getId();
    }


}
