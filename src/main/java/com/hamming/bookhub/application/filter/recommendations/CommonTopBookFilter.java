package com.hamming.bookhub.application.filter.recommendations;

public record CommonTopBookFilter(
        Integer pageSize,
        Integer pageNumber
) {
}
