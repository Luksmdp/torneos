package com.torneos.futbol.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpServerErrorException;

public class BadRequestException extends HttpServerErrorException {

    public BadRequestException(String message) {
        super(HttpStatusCode.valueOf(400),message);
    }
}

