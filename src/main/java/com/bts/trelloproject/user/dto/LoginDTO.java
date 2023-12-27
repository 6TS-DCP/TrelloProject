package com.bts.trelloproject.user.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    public record Request(
            @NotBlank String username,
            @NotBlank String password) {
    }
}