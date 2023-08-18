package com.decode.msapp.users.controllers;

import com.decode.msapp.users.exception.CantCheckUserForFraudExeption;
import com.decode.msapp.users.exception.UserIsFraudsterExeption;
import com.decode.msapp.users.exception.UserNotFoundExeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerGlobal {
    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<String> handleCustomException(final CantCheckUserForFraudExeption ex) {
        log.info("ControllerAdvice intercepted exception");
        return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIsFraudsterExeption.class)
    public ResponseEntity<String> handleCustomException(final UserIsFraudsterExeption ex) {
        log.info("ControllerAdvice intercepted exception");
        return new ResponseEntity<>("User is fraudster", HttpStatus.FORBIDDEN);
    }
}
