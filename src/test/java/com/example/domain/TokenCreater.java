package com.example.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
@Transactional
@Rollback
public class TokenCreater {

    @Test
    void tokenCreater(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[64]; // 64 bytes = 512 bits
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("\n\n\n");
        System.out.println(encodedKey);
        System.out.println("\n\n\n");
    }
}
