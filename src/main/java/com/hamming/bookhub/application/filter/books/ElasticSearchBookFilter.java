package com.hamming.bookhub.application.filter.books;

public record ElasticSearchBookFilter(
        String textQuery,
        Integer pageSize,
        Integer pageNumber
) {
}
