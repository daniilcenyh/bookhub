package com.hamming.bookhub.application.filter.books;


public record BooksSearchByAuthorFilter(
        Integer pageSize,
        Integer pageNumber,
        String author
) {
}
