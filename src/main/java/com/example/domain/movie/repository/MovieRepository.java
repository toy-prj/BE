package com.example.domain.movie.repository;

import com.example.domain.movie.domain.Movie;
import com.example.domain.movie.domain.response.MovieListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByName(String name);

//    Page<Movie> findAll(Pageable pageable);
}
