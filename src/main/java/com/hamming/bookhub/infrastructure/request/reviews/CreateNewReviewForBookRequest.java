package com.hamming.bookhub.infrastructure.request.reviews;

import java.util.UUID;

public record CreateNewReviewForBookRequest (

        UUID bookId,

        UUID userId,

        String feedback,

        Double rating
) {
}
