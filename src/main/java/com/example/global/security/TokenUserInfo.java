package com.example.global.security;

import com.example.domain.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenUserInfo {

    private Long userId;
    private String mail;
    private Role role;

    public String getRole(){
        return "ROLE_" + role;
    }
}
