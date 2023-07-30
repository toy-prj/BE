package com.example.domain.schedule.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class SaveScheduleRequestDto {

    @NotBlank(message = "영화 시간대는 필수 입력값입니다.")
    private LocalDateTime time;

    @NotBlank(message = "영화 id는 필수 값입니다.")
    private Long movieId;

    @NotBlank(message = "상영관 id는 필수 값입니다.")
    private Long theaterId;



}
