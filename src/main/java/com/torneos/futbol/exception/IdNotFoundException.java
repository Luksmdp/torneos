package com.torneos.futbol.exception;

public class IdNotFoundException extends RuntimeException {
    private final int statusCode;

    public IdNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
