package com.decode.msapp.users.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthControllerErrors {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @GetMapping("/denied")
    public String accessDeniedStub() {
        return "You're authorized, but dont have rights";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @GetMapping("/askauth")
    public String authorizeStub() {
        return "Please authorize first";
    }


}
