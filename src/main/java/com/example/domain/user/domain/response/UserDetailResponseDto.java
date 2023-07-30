package com.example.domain.user.domain.response;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class UserDetailResponseDto {

    private String name;
    private LocalDate birthdate;
    private String phone;
    private String mail;
    private Rank rank;

    public UserDetailResponseDto(User user) {
        this.name = user.getName();
        this.phone = user.getPhone();
        this.birthdate = user.getBirthdate();
        this.rank = user.getRank();
        this.mail = user.getMail();
    }
}
