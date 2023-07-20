package com.decode.msapp.users.exception;

public class UserNotFoundExeption extends Exception {
    private final static String errorCode = "IAA-004";

    public UserNotFoundExeption(String message) {
        this.message = message;
    }

    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
