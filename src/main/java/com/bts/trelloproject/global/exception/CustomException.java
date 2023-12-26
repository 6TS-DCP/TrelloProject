package com.bts.trelloproject.global.exception;

import com.bts.trelloproject.global.common.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    StatusEnum statusEnum;
}