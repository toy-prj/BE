package com.example.domain.movie.controller;

import com.example.domain.movie.domain.request.SaveMovieRequestDto;
import com.example.domain.movie.domain.request.UpdateMovieRequestDto;
import com.example.domain.movie.domain.response.MovieListResponseDto;
import com.example.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieListResponseDto>> getMovieList(Pageable pageable) {
        return ResponseEntity.ok().body(movieService.getList(pageable));
    }

    @PostMapping
    public ResponseEntity<Boolean> saveMovie(@Validated @RequestBody SaveMovieRequestDto dto) {
        return ResponseEntity.ok().body(movieService.save(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> updateMovie(@PathVariable Long id, @Validated @RequestBody UpdateMovieRequestDto dto) {
        return ResponseEntity.ok().body(movieService.updateById(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteMovie(@PathVariable Long id) {
        return ResponseEntity.ok().body(movieService.deleteById(id));
    }








}
