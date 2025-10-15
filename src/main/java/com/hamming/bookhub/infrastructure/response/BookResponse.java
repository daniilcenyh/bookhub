package com.hamming.bookhub.infrastructure.response;

import com.hamming.bookhub.domain.model.enums.BookGenre;

import java.io.Serializable;
import java.util.UUID;

public record BookResponse (
        UUID id,
        String title,
        String description,
        String author,
        BookGenre genre,
        Double rating
) implements Serializable {
}
