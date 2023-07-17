package com.decode.msapp.users.exception;

public class UserIsNotEligibleForFraudTestExeption extends Exception {

        private final static String errorCode = "IAA-002";

        public UserIsNotEligibleForFraudTestExeption(String message) {
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

