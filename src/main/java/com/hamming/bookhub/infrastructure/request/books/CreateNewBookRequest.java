package com.hamming.bookhub.infrastructure.request.books;

import com.hamming.bookhub.domain.model.enums.BookGenre;
import com.hamming.bookhub.domain.model.enums.BookRating;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateNewBookRequest(
        @NotNull
        @NotBlank
        @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
        String title,

        @NotNull
        @NotBlank
        @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
        String description,

        @NotNull
        @NotBlank
        @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
        String author,

        @NotNull
        @Enumerated(value = EnumType.STRING)
        @Column(length = 32)
        BookGenre genre,

        @NotNull
        Double rating
) {
}
