package com.example.domain.movie.domain.response;

import com.example.domain.movie.domain.Movie;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MovieListResponseDto {

    private String name;
    private LocalDate release;
    private String genre;

    public MovieListResponseDto(Movie movie) {
        this.name = movie.getName();
        this.genre = movie.getGenre();
        this.release = movie.getRelease();
    }
}
