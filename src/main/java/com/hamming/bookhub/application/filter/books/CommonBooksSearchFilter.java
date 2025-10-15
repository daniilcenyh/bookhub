package com.hamming.bookhub.application.filter.books;

import com.hamming.bookhub.domain.model.enums.BookGenre;

public record CommonBooksSearchFilter(
        Integer pageSize,
        Integer pageNumber,
        BookGenre genre,
        String author
) {
}
