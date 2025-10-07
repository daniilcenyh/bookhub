package com.hamming.bookhub.infrastructure.request.recommendations;

import com.hamming.bookhub.domain.model.enums.BookGenre;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record RecommendationGenreRequest(
        @NotNull
        @Enumerated(value = EnumType.STRING)
        @Column(length = 32)
        BookGenre genre
) {
}
