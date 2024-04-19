package com.macromill.user.exception;

import com.macromill.user.constant.Message;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(Message.FAIL_MESSAGE_USER_NOT_FOUND);
    }
}
