package com.decode.msapp.apigw.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CantValidateTokenException extends RuntimeException{
    private final String message;
}
