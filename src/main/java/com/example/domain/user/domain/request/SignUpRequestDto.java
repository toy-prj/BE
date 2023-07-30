package com.example.domain.user.domain.request;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.Role;
import com.example.domain.user.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    private String name;

    private LocalDate birthdate;

    private String phone;

    @NotBlank
    private String mail;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private Rank rank;


    public User toEntity(String encodedPassword) {

        return User.builder()
                .name(name)
                .phone(phone)
                .birthdate(birthdate)
                .mail(mail)
                .role(role)
                .rank(rank)
                .password(encodedPassword)
                .build();
    }

}
