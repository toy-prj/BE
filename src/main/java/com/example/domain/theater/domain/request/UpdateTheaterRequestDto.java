package com.example.domain.theater.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTheaterRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String location;
}
