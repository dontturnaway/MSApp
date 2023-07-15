package com.decode.msapp.users.exception;

public class CantCreateUserExeption extends Exception {
    private final static String errorCode = "IAA-001";

    public CantCreateUserExeption(String message) {
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
