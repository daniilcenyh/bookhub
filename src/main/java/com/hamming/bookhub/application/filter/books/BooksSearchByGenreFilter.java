package com.hamming.bookhub.application.filter.books;

import com.hamming.bookhub.domain.model.enums.BookGenre;

public record BooksSearchByGenreFilter(
        Integer pageSize,
        Integer pageNumber,
        BookGenre genre
) {
}
