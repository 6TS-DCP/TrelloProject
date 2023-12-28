package com.bts.trelloproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class UpdateProfileDTO {

    public record Request(
            @NotBlank @Size(max = 255)String introduce,
            @NotBlank String password) {

    }

//    @Builder
//    public record Response(String username, String Introduce) {
//
//        public static Response of(User user) {
//            return Response.builder()
//                    .username(user.getUsername())
//                    .Introduce(user.getIntroduce())
//                    .build();
//        }
//    }
}
