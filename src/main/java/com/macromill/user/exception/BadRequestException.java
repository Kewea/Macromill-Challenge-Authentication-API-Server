package com.macromill.user.exception;

public class BadRequestException extends BaseException {
    public BadRequestException(String message, String cause) {
        super(message, cause);
    }
}
