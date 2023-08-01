package com.decode.msapp.users.exception;

public class CantCheckUserForFraudExeption extends RuntimeException {
    private static final String ERROR_CODE = "IAA-004";
    private final String message;

    public CantCheckUserForFraudExeption(String message) {
        this.message = ERROR_CODE + message;
    }



}
