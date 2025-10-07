package com.hamming.bookhub.infrastructure.request.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
