package com.example.domain.theater.service;

import com.example.domain.theater.domain.request.SaveTheaterRequestDto;
import com.example.domain.theater.service.TheaterService;
import com.example.domain.user.domain.Role;
import com.example.domain.user.domain.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static com.example.domain.user.domain.Role.ADMIN;
import static com.example.domain.user.domain.Role.COMMON;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class TheaterServiceTest {

    @Autowired
    TheaterService theaterService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("관리자는 극장을 등록할 수 있어야 한다.")
    void addTheater() {
        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(ADMIN)
                .password("1111")
                .build());

        Role role = admin.getRole();
        // when
        SaveTheaterRequestDto build = SaveTheaterRequestDto.builder()
                .name("강남1관")
                .location("강남구 강남동 강남길")
                .build();

        // then
        assertTrue(theaterService.save(build));

    }

    @Test
    @DisplayName("관리자가 아니라면 극장을 등록할 수 없어야 한다.")
    void addTheaterNoRole() {
        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(COMMON)
                .password("1111")
                .build());

        Role role = admin.getRole();
        // when
        SaveTheaterRequestDto build = SaveTheaterRequestDto.builder()
                .name("강남1관")
                .location("강남구 강남동 강남길")
                .build();

        // then
        assertThrows(CustomException.class, () -> theaterService.save(build));

    }

    @Test
    @DisplayName("관리자가 같은 극장을 등록하면 에러가 떠야한다.")
    void addTheaterDuplicated() {
        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(ADMIN)
                .password("1111")
                .build());

        Role role = admin.getRole();

        // when
        SaveTheaterRequestDto build = SaveTheaterRequestDto.builder()
                .name("강남1관")
                .location("강남구 강남동 강남길")
                .build();
        SaveTheaterRequestDto build1 = SaveTheaterRequestDto.builder()
                .name("강남1관")
                .location("강남구 강남동 강남길")
                .build();

        theaterService.save(build);
        // then

        assertThrows(CustomException.class, () -> theaterService.save(build1));

    }

}