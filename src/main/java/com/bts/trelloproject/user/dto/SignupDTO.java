package com.bts.trelloproject.user.dto;

import com.bts.trelloproject.user.constant.Provider;
import com.bts.trelloproject.user.constant.UserRoleEnum;
import jakarta.validation.constraints.Pattern;
import com.bts.trelloproject.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignupDTO(
        @Pattern(regexp = "^[a-z0-9]{4,10}$") String username,
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*?_]{8,15}$") String password) {

    private static final String BASIC_PROFILE_INTRODUCTION = "Hello!";
    private static final String BASIC_PROFILE_EMAIL = "myEmail@email.com";

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .introduce(BASIC_PROFILE_INTRODUCTION)
                .email(BASIC_PROFILE_EMAIL)
                .provider(Provider.LOCAL)
                .userRoleEnum(UserRoleEnum.USER)
                .build();
    }
}
