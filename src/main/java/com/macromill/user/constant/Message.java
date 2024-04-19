package com.macromill.user.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {
    public String SUCCESS_GET_USER = "User details by ";
    public String SUCCESS_MESSAGE_CREATE_USER = "Account successfully created";
    public String SUCCESS_MESSAGE_UPDATE_USER = "User successfully updated";
    public String SUCCESS_MESSAGE_DELETE_USER = "Account and user successfully removed";

    public String FAIL_MESSAGE_DUPLICATE_USER = "Account creation failed";
    public String FAIL_MESSAGE_USER_NOT_FOUND = "No User found";
    public String FAIL_MESSAGE_AUTHENTICATION = "Authentication Failed";
    public String FAIL_MESSAGE_USER_UPDATE = "User update failed";
    public String FAIL_MESSAGE_USER_NO_PERMISSION = "No permission for ACTION";

    public String FAIL_CAUSE_DUPLICATE_USER = "already same USERID is used";
    public String FAIL_CAUSE_USER_UPDATE = "Not updatable for user_id and password";
}
