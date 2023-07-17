package com.decode.msapp.users.exception;

public class UserIsFraudsterExeption extends Exception {

        private final static String errorCode = "IAA-002";

        public UserIsFraudsterExeption(String message) {
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

