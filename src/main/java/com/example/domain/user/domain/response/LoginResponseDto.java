package com.example.domain.user.domain.response;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDto {


    private String name;
    private Rank rank;
    private String phone;
    private String token;

    public LoginResponseDto(User user) {
        this.name = user.getName();
        this.rank = user.getRank();
        this.phone = user.getPhone();
//        this.token = token;
    }
}
