package com.decode.msapp.users.exception;

public class UserNotFoundExeption extends RuntimeException {
    private static final String ERROR_CODE = "IAA-004: ";
    private final String message;

    public UserNotFoundExeption(String message) {
        this.message = ERROR_CODE + message;
    }



}
