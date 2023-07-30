package com.example.domain.movie.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.movie.domain.request.SaveMovieRequestDto;
import com.example.domain.movie.domain.request.UpdateMovieRequestDto;
import com.example.domain.movie.domain.response.MovieListResponseDto;
import com.example.domain.movie.repository.MovieRepository;
import com.example.domain.user.domain.Role;
import com.example.domain.user.domain.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.domain.user.domain.Role.ADMIN;
import static com.example.domain.user.domain.Role.COMMON;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void inputMovie() {
        Movie test1 = Movie.builder()
                .name("test1")
                .genre("코미디")
                .release(LocalDate.parse("2023-06-29"))
                .build();
        Movie test2 = Movie.builder()
                .name("test2")
                .genre("멜로")
                .release(LocalDate.parse("2023-06-29"))
                .build();
        Movie test3 = Movie.builder()
                .name("test3")
                .genre("액션")
                .release(LocalDate.parse("2023-06-29"))
                .build();

        movieRepository.save(test1);
        movieRepository.save(test2);
        movieRepository.save(test3);


    }

    @Test
    @DisplayName("영화 목록을 전체 조회 할 수 있다.")
    void getMovieListTest() {

        String name = "test9";

        // given
        for (int i = 0; i < 60 ; i++) {

            movieRepository.save(Movie.builder()
                    .name("test" + i + "")
                    .genre("코미디")
                    .release(LocalDate.parse("2023-06-29"))
                    .build());
        }


        Pageable pageable = PageRequest.of(5, 10, Sort.by ("id").descending());


        // when
        List<MovieListResponseDto> movieList = movieService.getList(pageable);

        for (MovieListResponseDto dto : movieList) {
            System.out.println(dto.toString());
        }

        // then
        assertEquals(name, movieList.get(0).getName());

    }

    @Test
    @DisplayName("관리자는 영화를 등록 할 수 있다.")
    void addMovieTest() {

        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .role(ADMIN)
                .mail("test1")
                .password("1111")
                .build());

        Role role = admin.getRole();

        // when
        SaveMovieRequestDto dto = SaveMovieRequestDto.builder()
                .name("타짜")
                .genre("액션")
                .release(LocalDate.parse("2017-01-05"))
                .build();

        boolean flag = movieService.save(dto);


        // then
        assertTrue(flag);
    }

    @Test
    @DisplayName("관리자가 아니면 영화를 등록 할 수 없어야 한다.")
    void addMovieNoRoleTest() {

        // given
        User user = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(COMMON)
                .password("1111")
                .build());

        Role role = user.getRole();

        // when
        SaveMovieRequestDto dto = SaveMovieRequestDto.builder()
                .name("타짜")
                .genre("액션")
                .release(LocalDate.parse("2017-01-05"))
                .build();


        // then
        assertThrows(CustomException.class, () -> movieService.save(dto));

    }

    @Test
    @DisplayName("관리자는 영화를 삭제 할 수 있어야 한다.")
    void deleteMovie() {

        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(ADMIN)
                .password("1111")
                .build());

        Role role = admin.getRole();

        Movie movie = movieRepository.save(Movie.builder()
                .name("타짜")
                .genre("액션")
                .release(LocalDate.parse("2023-06-30"))
                .build());

        // when
        Boolean flag = movieService.deleteById(movie.getId());

        // then
        assertTrue(flag);
    }

    @Test
    @DisplayName("관리자가 아니면 영화를 삭제 할 수 없어야 한다.")
    void deleteMovieNoRole() {

        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(COMMON)
                .password("1111")
                .build());

        Role role = admin.getRole();

        Movie movie = movieRepository.save(Movie.builder()
                .name("타짜")
                .genre("액션")
                .release(LocalDate.parse("2023-06-30"))
                .build());

        // when

        // then
        assertThrows(CustomException.class, () -> movieService.deleteById(movie.getId()));
    }


    @Test
    @DisplayName("관리자는 영화의 정보를 수정 할 수 잇어야 한다.")
    void updateMovie() {

        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(ADMIN)
                .password("1111")
                .build());

        Role role = admin.getRole();

        Movie movie = movieRepository.save(Movie.builder()
                .name("타짜")
                .genre("액션")
                .release(LocalDate.parse("2023-06-30"))
                .build());

        UpdateMovieRequestDto dto = UpdateMovieRequestDto.builder()
                .name("타조")
                .genre("없음")
                .release(LocalDate.parse("2023-06-30"))
                .build();

        // when
        movieService.updateById(movie.getId(), dto);

        // then
        assertEquals("타조", movie.getName());
    }
    @Test
    @DisplayName("영화 리스트를 내림차순으로 가져온다.")
    void findMovies(){

        Pageable pageable = PageRequest.of(0,10, Sort.by ("id").descending());

        List<MovieListResponseDto> movieList = movieService.getList(pageable);
        Assertions.assertThat(movieList).isNotEmpty();
    }



}