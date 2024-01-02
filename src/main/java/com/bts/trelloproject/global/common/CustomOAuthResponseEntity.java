package com.bts.trelloproject.global.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomOAuthResponseEntity<T> implements Serializable {

    private HttpStatus status;
    private Integer code;
    private String message;
    private T data;

    public static <T> CustomOAuthResponseEntity<T> success(T data) {
        return CustomOAuthResponseEntity.<T>builder()
                .status(StatusEnum.SUCCESS.getHttpStatus())
                .code(StatusEnum.SUCCESS.getHttpStatus().value())
                .message(StatusEnum.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> CustomOAuthResponseEntity<T> error(StatusEnum statusEnum) {
        return CustomOAuthResponseEntity.<T>builder()
                .status(statusEnum.getHttpStatus())
                .code(statusEnum.getHttpStatus().value())
                .message(statusEnum.getMessage())
                .build();
    }
}
