package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.application.service.UserService;
import com.hamming.bookhub.infrastructure.request.users.RegistrationNewUserRequest;
import com.hamming.bookhub.infrastructure.request.users.UpdateUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> registerNewUser(
            @RequestBody @Validated RegistrationNewUserRequest request,
            BindingResult bindingResult
    ) throws BindException {
        log.info("REGISTRATION_NEW_USER_REQUEST. Time request: {}", LocalDateTime.now());

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(request));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable(value = "id") UUID id
    ) {
        log.info("GET_USER_BY_UUID. Time request: {}", LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> updateUserById(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Validated UpdateUserRequest request
    ) {
        log.info("UPDATE_USER_FIELDS_BY_UUID. Time request: {}", LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUserById(
            @PathVariable(value = "id") UUID id
    ) {
        log.info("DELETE_USER_BY_UUID. Time request: {}", LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
