package com.hamming.bookhub.infrastructure.request.reviews;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CreateNewReviewForBookRequest (

        @NotNull
        @NotBlank
        UUID bookId,

        @NotNull
        @NotBlank
        UUID userId,

        @NotNull
        @NotBlank
        @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
        String feedback,

        @NotNull
        @PositiveOrZero
        @Max(value = 5)
        Double rating
) {
}
