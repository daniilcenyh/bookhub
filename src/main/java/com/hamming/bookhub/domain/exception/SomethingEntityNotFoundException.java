package com.hamming.bookhub.domain.exception;

public class SomethingEntityNotFoundException extends RuntimeException {
    public SomethingEntityNotFoundException(String message) {
        super(message);
    }
}
