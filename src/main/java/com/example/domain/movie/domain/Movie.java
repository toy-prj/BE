package com.example.domain.movie.domain;

import com.example.domain.movie.domain.request.UpdateMovieRequestDto;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
public class Movie {


    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_name")
    private String name;

    @Column(name = "movie_release")
    private LocalDate release;

    @Column(name = "movie_genre")
    private String genre;

    @Column(name = "movie_photo")
    private String photo;


    public void updateMovie(UpdateMovieRequestDto dto) {
        this.name = dto.getName();
        this.genre = dto.getGenre();
        this.release  = dto.getRelease();
    }
}
