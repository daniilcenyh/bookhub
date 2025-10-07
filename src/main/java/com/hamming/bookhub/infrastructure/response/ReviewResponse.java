package com.hamming.bookhub.infrastructure.response;

import java.util.UUID;

public record ReviewResponse (

        UUID _id,

        UUID bookId,

        UUID userId,

        String feedback,

        Double rating
) {

}
