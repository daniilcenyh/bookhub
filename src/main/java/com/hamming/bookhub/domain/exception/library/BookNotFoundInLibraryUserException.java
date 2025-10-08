package com.hamming.bookhub.domain.exception.library;

public class BookNotFoundInLibraryUserException extends RuntimeException {
    public BookNotFoundInLibraryUserException(String message) {
        super(message);
    }
}
