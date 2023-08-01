package com.decode.msapp.users.exception;

import lombok.Getter;

@Getter
public class CantCreateUserExeption extends RuntimeException {
    private static final String ERROR_CODE = "IAA-001";
    private final String message;

    public CantCreateUserExeption(String message) {
        this.message = ERROR_CODE + message;
    }

}
