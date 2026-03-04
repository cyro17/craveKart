package com.cyro.cravekart.exception;

public class BadRequestException extends  RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
