package com.bts.trelloproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileDTO(
        @NotBlank @Size(max = 255) String introduce,
        @NotBlank String password) {

}