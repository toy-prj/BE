package com.example.domain.movie.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.movie.domain.request.SaveMovieRequestDto;
import com.example.domain.movie.domain.request.UpdateMovieRequestDto;
import com.example.domain.movie.domain.response.MovieListResponseDto;
import com.example.domain.movie.repository.MovieRepository;
import com.example.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.global.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieListResponseDto> getList(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .stream()
                .map(MovieListResponseDto::new).collect(Collectors.toList());
    }

    public boolean save(SaveMovieRequestDto dto) {
        findByName(dto.getName());
        movieRepository.save(dto.toEntity());
        return true;
    }

    public boolean updateById(Long id,UpdateMovieRequestDto dto) {
        findById(id).updateMovie(dto);
        return true;
    }

    public boolean deleteById(Long id) {
        movieRepository.delete(findById(id));
        return true;
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new CustomException(MOVIE_NOT_FOUND.getMessage(), MOVIE_NOT_FOUND));
    }

    private void findByName(String name) {
        movieRepository.findByName(name).ifPresent(x -> {
            throw new CustomException(DUPLICATE_MOVIE.getMessage(), DUPLICATE_MOVIE);
        });
    }


}
