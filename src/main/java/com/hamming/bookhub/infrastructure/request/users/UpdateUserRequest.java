package com.hamming.bookhub.infrastructure.request.users;

import com.hamming.bookhub.infrastructure.response.BookResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateUserRequest (
        @NotNull
        @NotBlank
        @Size(min = 5, max = 100, message = "Username must be between 5 and 100 characters")
        String username,

        @Email
        @NotNull
        @NotBlank
        String email
) {
}
