package com.hamming.bookhub.application.filter.reviews;

import java.util.UUID;

public record ReviewSearchFilter(
        Integer pageSize,
        Integer pageNumber,
        UUID bokId
) {
}
