package com.example.domain.seat.domain.request;

import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.Sold;
import com.example.domain.theater.domain.Theater;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class SaveSeatRequestDto {

    @NotBlank
    private String name;

    private Sold status;

    private Theater theaterId;
    public Seat toEntity() {
        return Seat.builder()
                .name(name)
                .theater(theaterId)
                .status(status)
                .build();
    }
}
