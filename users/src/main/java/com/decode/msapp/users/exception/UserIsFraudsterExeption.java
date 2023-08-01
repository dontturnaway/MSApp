package com.decode.msapp.users.exception;

import lombok.Getter;

@Getter
public class UserIsFraudsterExeption extends RuntimeException {

    private static final String ERROR_CODE = "IAA-002";
    private final String message;

    public UserIsFraudsterExeption(String message) {
        this.message = ERROR_CODE + message;
    }

}

