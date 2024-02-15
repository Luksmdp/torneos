package com.torneos.futbol.exception;

public class DataNullException extends RuntimeException {
    private final int statusCode;

    public DataNullException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
