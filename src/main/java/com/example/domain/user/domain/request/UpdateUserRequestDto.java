package com.example.domain.user.domain.request;

import com.example.domain.user.domain.User;
import lombok.*;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {


    private String name;
    private LocalDate birthdate;
    private String phone;
    private String mail;
    private String password;


    public UpdateUserRequestDto(User user) {
        this.name = user.getName();
        this.birthdate = user.getBirthdate();
        this.phone = user.getPhone();
        this.mail = user.getMail();
        this.password = user.getPassword();
    }

}
