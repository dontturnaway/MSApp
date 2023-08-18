package com.decode.msapp.apigw.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthHeaderMissingException extends RuntimeException{
    private final String message;
}
