package com.decode.msapp.users.exception;

import lombok.Getter;

@Getter
public class WrongInputParameters extends RuntimeException {

    private static final String ERROR_CODE = "IAA-003";
    private final String message;

        public WrongInputParameters(String message) {
            this.message = ERROR_CODE + message;
        }
}

