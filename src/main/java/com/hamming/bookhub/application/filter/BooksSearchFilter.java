package com.hamming.bookhub.application.filter;

public record BooksSearchFilter(
        Integer pageSize,
        Integer pageNumber
) {
}
