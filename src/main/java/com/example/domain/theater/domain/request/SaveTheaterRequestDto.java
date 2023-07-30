package com.example.domain.theater.domain.request;

import com.example.domain.theater.domain.Theater;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveTheaterRequestDto {

    private String name;
    private String location;

    public Theater toEntity() {
        return Theater.builder()
                .location(location)
                .name(name)
                .build();
    }
}
