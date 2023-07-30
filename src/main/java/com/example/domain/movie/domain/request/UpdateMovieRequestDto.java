package com.example.domain.movie.domain.request;

import com.example.domain.movie.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieRequestDto {

    @NotBlank
    private String name;
    @NotNull
    private LocalDate release;
    @NotBlank
    private String genre;

}
