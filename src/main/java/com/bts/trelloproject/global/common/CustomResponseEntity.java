package com.bts.trelloproject.global.common;

import lombok.Builder;
import lombok.Data;
import com.bts.trelloproject.global.security.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

@Data
@Builder
public class CustomResponseEntity {
    private int status;
    private String description;
    private String message;

    public static ResponseEntity<CustomResponseEntity> toResponseEntity(StatusEnum e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(CustomResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .description(e.name())
                        .message(e.getMessage())
                        .build());
    }

    public static ResponseEntity<CustomResponseEntity> toResponseEntity(BindException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomResponseEntity.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .description("VALID_FAILED")
                        .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                        .build());
    }

    public static ResponseEntity<CustomResponseEntity> toResponseEntityWithHeader(StatusEnum e, String token){
        return ResponseEntity
                .status(e.getHttpStatus())
                .header(JwtUtil.AUTHORIZATION_HEADER, token)
                .body(CustomResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .description(e.name())
                        .message(e.getMessage())
                        .build());
    }

    public static CustomResponseEntity toCustomResponseEntity(StatusEnum e){
        return CustomResponseEntity.builder()
                .status(e.getHttpStatus().value())
                .description(e.name())
                .message(e.getMessage())
                .build();
    }
}

