package com.macromill.user.exception;

import com.macromill.user.constant.Message;

public class AuthorizationException extends BaseException {
    public AuthorizationException() {
        super(Message.FAIL_MESSAGE_AUTHENTICATION);
    }
}
