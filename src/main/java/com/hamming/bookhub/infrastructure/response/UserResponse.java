package com.hamming.bookhub.infrastructure.response;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        List<BookResponse> library
) {
}
