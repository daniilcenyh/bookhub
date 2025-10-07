package com.hamming.bookhub.infrastructure.response;

import com.hamming.bookhub.domain.model.enums.BookGenre;
import com.hamming.bookhub.domain.model.enums.BookRating;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        String description,
        String author,
        BookGenre genre,
        BookRating rating
) {
}
