package com.hamming.bookhub.shared.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String typeError,
        String errorMessage,
        LocalDateTime timeOfError
) {
}