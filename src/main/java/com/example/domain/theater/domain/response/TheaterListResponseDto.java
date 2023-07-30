package com.example.domain.theater.domain.response;

import com.example.domain.theater.domain.Theater;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TheaterListResponseDto {

    private String name;
    private String location;

    public TheaterListResponseDto(Theater theater) {
        this.name = theater.getName();
        this.location = theater.getLocation();
    }
}
