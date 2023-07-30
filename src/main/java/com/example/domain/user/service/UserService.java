package com.example.domain.user.service;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.request.UpgradeUserRankRequestDto;
import com.example.domain.user.domain.request.UpdateUserRequestDto;
import com.example.domain.user.domain.response.LoginResponseDto;
import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.domain.user.domain.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


import static com.example.global.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public SignUpResponseDto signUp(final SignUpRequestDto dto) {
        isDuplicatedMail(dto.getMail());
        String encodedPassword = encodePassword(dto.getPassword());
        User saved = userRepository.save(dto.toEntity(encodedPassword));
        return new SignUpResponseDto(saved);
    }

    public LoginResponseDto logIn(LoginRequestDto dto) {
        User user = findByEmail(dto.getMail());
        checkPassword(dto.getPassword(), user.getPassword());
//        String token = tokenProvider.createToken(user);
        return new LoginResponseDto(user);
    }

    public UserDetailResponseDto getDetail(Long id) {
        return getUserDetailResponseDto(id);
    }

    public UserDetailResponseDto update(UpdateUserRequestDto dto, Long id) {
        encodePassword(dto.getPassword());
        findById(id).updateUser(dto);
        return getUserDetailResponseDto(id);
    }

    public boolean signOut(Long id) {
        userRepository.delete(findById(id));
        return true;
    }

    public boolean upgradeRank(UpgradeUserRankRequestDto dto) {
        User user = findById(dto.getUserId());
        Rank newRank = dto.getNewRank();

        validateUserRank(user.getRank(), newRank);
        return user.upgradeRank(newRank);
    }

    private static void validateUserRank(Rank rawRank, Rank newRank) {
        if (rawRank.equals(newRank)) {
            throw new CustomException(SAME_RANK.getMessage(), SAME_RANK);
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND.getMessage(), USER_NOT_FOUND));

    }

    private User findByEmail(String mail) {
        return userRepository.findByMail(mail)
                .orElseThrow(() -> new CustomException(MAIL_NOT_FOUND.getMessage(), MAIL_NOT_FOUND));

    }

    private void checkPassword(String inputPassword, String rawPassword) {
        if (!encoder.matches(inputPassword, rawPassword)) {
            throw new CustomException(WRONG_PASSWORD.getMessage(), WRONG_PASSWORD);
        }
    }

    private void isDuplicatedMail(String mail) {
        userRepository.findByMail(mail)
                .ifPresent(x -> {
                    throw new CustomException(DUPLICATE_MAIL.getMessage(), DUPLICATE_MAIL);
                });
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }

    private UserDetailResponseDto getUserDetailResponseDto(Long id) {
        return userRepository.findById(id).map(UserDetailResponseDto::new)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND.getMessage(), USER_NOT_FOUND));
    }


}
