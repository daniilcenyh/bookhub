package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.application.service.UserService;
import com.hamming.bookhub.infrastructure.request.users.RegistrationNewUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerNewUser(
            @RequestBody @Validated RegistrationNewUserRequest request
    ) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable(value = "id") UUID id
    ) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserById(
            @PathVariable(value = "id") UUID id
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable(value = "id") UUID id
    ) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
