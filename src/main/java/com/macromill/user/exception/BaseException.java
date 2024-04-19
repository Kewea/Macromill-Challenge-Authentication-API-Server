package com.macromill.user.exception;

import com.macromill.user.dto.ErrorMessageDto;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorMessageDto errorMessageDto;

    public BaseException(String message, String cause) {
        super();
        this.errorMessageDto = ErrorMessageDto.builder().message(message).cause(cause).build();
    }

    public BaseException(String message) {
        super();
        this.errorMessageDto = ErrorMessageDto.builder().message(message).build();
    }
}
