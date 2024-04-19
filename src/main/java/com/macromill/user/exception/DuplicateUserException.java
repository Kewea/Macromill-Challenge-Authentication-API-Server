package com.macromill.user.exception;

import com.macromill.user.constant.Message;
import com.macromill.user.dto.ErrorMessageDto;

public class DuplicateUserException extends BaseException {
    public DuplicateUserException(String username) {
        super(Message.FAIL_MESSAGE_DUPLICATE_USER, Message.FAIL_CAUSE_DUPLICATE_USER.replace("USERID", username));
    }
}
