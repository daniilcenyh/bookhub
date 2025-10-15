package com.hamming.bookhub.domain.exception.reviews;

public class ReviewDocumentNotFoundException extends RuntimeException {
    public ReviewDocumentNotFoundException(String message) {
        super(message);
    }
}
