package com.example.domain.user.service;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.request.UpdateUserRequestDto;
import com.example.domain.user.domain.request.UpgradeUserRankRequestDto;
import com.example.domain.user.domain.response.LoginResponseDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import javax.transaction.Transactional;
import java.time.LocalDate;

import static com.example.domain.user.domain.Role.ADMIN;
import static com.example.domain.user.domain.Role.COMMON;
import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@Rollback(value = true)
class UserServiceTest {


    @Autowired
    UserService userService;

    @Autowired
    UserRepository repository;

    @BeforeEach
    void signUpBefore() {
        SignUpRequestDto dto = SignUpRequestDto.builder()
                .name("admin")
                .password("!23")
                .mail("admin")
                .role(ADMIN)
                .build();
        userService.signUp(dto);
        SignUpRequestDto dto1 = SignUpRequestDto.builder()
                .name("user")
                .password("123")
                .mail("user")
                .birthdate(LocalDate.parse("2020-02-02"))
                .build();
        userService.signUp(dto1);


    }

    @Test
    @DisplayName("회원가입이 정상적으로 이루어지고 비밀번호가 인코딩이 되어야한다.")
    void signUpPwTest() {

        // given
        SignUpRequestDto dto = SignUpRequestDto.builder()
                .name("나나1")
                .password("111111")
                .mail("admin1")
                .build();


        // when
        SignUpResponseDto flag = userService.signUp(dto);

        // then
        assertEquals("나나1", flag.getName());

    }

    @Test
    @DisplayName("중복된 아이디로 회원가입을 시도하면 에러가 떠야한다.")
    void signUpTest() {

        // given
        SignUpRequestDto dto = SignUpRequestDto.builder()
                .name("admin")
                .password("!23")
                .mail("admin")
                .build();

        // when


        // then
        assertThrows(CustomException.class,
                () -> {
                    userService.signUp(dto);
                }
        );
    }


    @Test
    @DisplayName("로그인이 정상적으로 실행되어야한다.")
    void loginTest() {

        // given
        LoginRequestDto dto = LoginRequestDto.builder()
                .mail("admin")
                .password("!23")
                .build();


        // when
        LoginResponseDto logedIn = userService.logIn(dto);

        System.out.println("logedIn = " + logedIn);
        // then
        assertEquals("admin", logedIn.getName());

    }

    @Test
    @DisplayName("회원정보를 수정하면 db에 반영 되어야 한다.")
    void updateUserTest() {

        String newMail = "hello1";

        // given
        User user = User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .rank(Rank.GOLD)
                .phone("010-0000-0000")
                .mail("test1")
                .password("1111")
                .build();
        User saved = repository.save(user);
        Long id = saved.getId();

        UpdateUserRequestDto dto = UpdateUserRequestDto.builder()
                .name("안녕")
                .password("22555555")
                .mail("hello1")
                .birthdate(LocalDate.parse("1999-10-10"))
                .phone("010-222-1111")
                .build();


        // when
        UserDetailResponseDto responseDto = userService.update(dto, id);

        // then
        assertEquals(newMail, responseDto.getMail());
    }

    @Test
    @DisplayName("회원 아이디로 마이 페이지를 조회 할 수 있어야한다.")
    void myPageTest() {

        // given
        String name = "test1";
        User user = User.builder()
                .name(name)
                .birthdate(LocalDate.parse("2020-01-01"))
                .rank(Rank.GOLD)
                .role(ADMIN)
                .phone("010-0000-0000")
                .mail("test1")
                .password("1111")
                .build();
        User saved = repository.save(user);
        Long id = saved.getId();

        // when
        UserDetailResponseDto dto = userService.getDetail(id);
        System.out.println("dto = " + dto);


        // then
        assertEquals(name, dto.getName());
    }

    @Test
    @DisplayName("회원 아이디로 회원 탈퇴를 할 수 있어야 한다.")
    void withdrawTest() {

        // given
        String name = "test1";
        User user = User.builder()
                .name(name)
                .birthdate(LocalDate.parse("2020-01-01"))
                .rank(Rank.GOLD)
                .role(ADMIN)
                .phone("010-0000-0000")
                .mail("test1")
                .password("1111")
                .build();
        User saved = repository.save(user);
        Long id = saved.getId();

        // when
        boolean flag = userService.signOut(id);

        // then
        assertTrue(flag);
    }

    @Test
    @DisplayName("잘못된 회원 코드로 회원 탈퇴를 시도하면 에러가 발생한다.")
    void withdrawFalseTest() {

        // given
        Long id = 6L;

        // then
        assertThrows(CustomException.class,
                () -> {
                    userService.signOut(id);
                }
        );

    }

    @Test
    @DisplayName("관리자는 유저의 랭크를 업그레이드 시킬 수 있다.")
    void upgradeUserRank() {

        // given
        User admin = repository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .role(ADMIN)
                .mail("test1")
                .password("1111")
                .build());

        User savedUser = repository.save(User.builder()
                .name("user")
                .birthdate(LocalDate.parse("2020-01-01"))
                .role(COMMON)
                .phone("010-0000-0000")
                .mail("test2")
                .password("1111")
                .build());

        Long userId = savedUser.getId();


        //given
        UpgradeUserRankRequestDto dto = UpgradeUserRankRequestDto.builder()
                .newRank(Rank.GOLD)
                .userId(userId)
                .build();

        // when
        boolean flag = userService.upgradeRank(dto);

        // then
        Assertions.assertTrue(flag);


    }

    @Test
    @DisplayName("관리자가 유저의 이전 랭크와 동일한 랭크를 설정하면 false 여야한다")
    void checkUserRankErrorTest() {

        // given
        User admin = repository.save(User.builder()
                .name("test1")
                .birthdate(LocalDate.parse("2020-01-01"))
                .role(COMMON)
                .mail("test1")
                .password("1111")
                .build());

        User savedUser = repository.save(User.builder()
                .name("user")
                .birthdate(LocalDate.parse("2020-01-01"))
                .role(COMMON)
                .phone("010-0000-0000")
                .mail("test2")
                .password("1111")
                .build());

        Long userId = savedUser.getId();


        //given
        UpgradeUserRankRequestDto dto = UpgradeUserRankRequestDto.builder()
                .newRank(Rank.STANDARD)
                .userId(userId)
                .build();

        // then
        assertThrows(CustomException.class, () -> userService.upgradeRank(dto));


    }


}