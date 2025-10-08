package com.hamming.bookhub.infrastructure.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.UUID;

public record ReviewResponse (

        @JsonSerialize(using = ToStringSerializer.class)
        UUID _id,
        @JsonSerialize(using = ToStringSerializer.class)
        UUID bookId,
        @JsonSerialize(using = ToStringSerializer.class)
        UUID userId,
        String feedback,
        Double rating
) {

}
