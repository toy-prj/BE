package com.example.domain.user.controller;

import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.UpgradeUserRankRequestDto;
import com.example.domain.user.domain.request.UpdateUserRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.response.LoginResponseDto;
import com.example.domain.user.domain.response.MyPaymentResponseDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.domain.user.service.UserFacadeService;
import com.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserFacadeService facadeService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Validated @RequestBody SignUpRequestDto dto) {
            return ResponseEntity.ok().body(userService.signUp(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> logIn(@Validated @RequestBody LoginRequestDto dto){
        return ResponseEntity.ok().body(userService.logIn(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponseDto> getUserDetail(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getDetail(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDetailResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDto dto){
        return ResponseEntity.ok().body(userService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> signOut(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.signOut(id));
    }

    @PatchMapping
    public ResponseEntity<Boolean> upgradeRank(@Validated UpgradeUserRankRequestDto dto){
        return ResponseEntity.ok().body(userService.upgradeRank(dto));
    }

    @GetMapping("/{id}/ticket")
    public ResponseEntity<List<MyPaymentResponseDto>> getTicket(@PathVariable Long id){
        return ResponseEntity.ok().body(facadeService.getTicket(id));
    }

    @PatchMapping("/{id}/{paymentId}")
    public ResponseEntity<Boolean> cancelTicket(@PathVariable Long id, @PathVariable Long paymentId){
        return ResponseEntity.ok().body(facadeService.cancel(id, paymentId));
    }

}
