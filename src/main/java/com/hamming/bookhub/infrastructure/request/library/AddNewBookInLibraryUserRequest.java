package com.hamming.bookhub.infrastructure.request.library;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddNewBookInLibraryUserRequest(
        @NotNull
        @NotBlank
        UUID bookId
) {
}
