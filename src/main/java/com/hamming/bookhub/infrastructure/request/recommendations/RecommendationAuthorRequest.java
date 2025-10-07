package com.hamming.bookhub.infrastructure.request.recommendations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecommendationAuthorRequest(
        @NotNull
        @NotBlank
        @Size(min = 5, max = 100, message = "Author name must be between 5 and 100 characters")
        String author
) {
}
