package com.torneos.futbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class BadRequestException extends HttpClientErrorException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

