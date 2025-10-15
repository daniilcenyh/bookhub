package com.hamming.bookhub.domain.exception.library;

public class BookAlreadyExistsInLibraryUserException extends RuntimeException {
    public BookAlreadyExistsInLibraryUserException(String message) {
        super(message);
    }
}
