package com.example.domain.user.repository;

import com.example.domain.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("ID 중복 체크 성공 테스트, 중복이 (true)되어야한다")
    void id(){
        // given
        String id = "hkd22";

        // when
        Optional<User> found = userRepository.findByMail(id);

        boolean flag = found.isPresent();
        // then
        assertEquals(true, flag);
    }
    @Test
    @DisplayName("ID 중복 체크 성공 테스트, 답이 empty 어야한다")
    void idFalse(){
        // given
        String id = "hkd1";

        // when
        Optional<User> found = userRepository.findByMail(id);

        // then
        assertEquals(empty(), found);
    }

}