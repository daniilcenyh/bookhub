package com.hamming.bookhub.application.filter.library;

public record LibrarySearchFilter (
        Integer pageSize,
        Integer pageNumber
) {
}
