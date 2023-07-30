package com.example.domain.seat.service;

import com.example.domain.seat.domain.request.SaveSeatRequestDto;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.service.SeatService;
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
import java.util.List;

import static com.example.domain.user.domain.Role.ADMIN;
import static com.example.domain.user.domain.Role.COMMON;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class SeatServiceTest {

    @Autowired
    SeatService seatService;

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("관리자는 좌석을 등록할 수 있다.")
    void addSeat() {
        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(ADMIN)
                .password("1111")
                .build());
        Role role = admin.getRole();
        boolean b = false;
        // when
        for (int i = 1; i < 10; i++) {
            SaveSeatRequestDto name = SaveSeatRequestDto.builder().name("A" + i).build();
            b = seatService.save(name);
        }

        // then
        assertTrue(b);
    }


    @Test
    @DisplayName("관리자가 아니면 권한이 없다는 에러가 나와야 한다.")
    void addSeatNoRole() {
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
        // then

        SaveSeatRequestDto name = SaveSeatRequestDto.builder().name("A").build();
        assertThrows(CustomException.class, () ->
                seatService.save(name));


    }


    @Test
    @DisplayName("관리자가 좌석을 등록할 때 같은 좌석이 존재하면 에러가 떠야한다.")
    void addSeatDuplicated() {
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
        // then
        SaveSeatRequestDto.builder().name("A").build();
        SaveSeatRequestDto name1 = SaveSeatRequestDto.builder().name("A").build();
        assertThrows(CustomException.class, () ->
                seatService.save(name1));

    }


    @Test
    @DisplayName("사용자와 관리자는 좌석 리스트를 조회 할 수 있다.")
    void seatList() {
        // given
        User admin = userRepository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(ADMIN)
                .password("1111")
                .build());
        Role role = admin.getRole();

        for (int i = 1; i <= 10; i++) {
            SaveSeatRequestDto name = SaveSeatRequestDto.builder().name("A" + i).build();
            seatService.save(name);
        }
        // when
        List<SeatListResponseDto> seatList = seatService.findAll();
        for (SeatListResponseDto d : seatList) {
            System.out.println("seatList = " + d.getName());
        }

        // then
        assertEquals(10, seatList.size());


    }

}