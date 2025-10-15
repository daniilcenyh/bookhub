package com.hamming.bookhub.domain.exception.books;

public class InvalidBookRatingException extends RuntimeException {
    public InvalidBookRatingException(String message) {
        super(message);
    }
}
