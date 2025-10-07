package com.hamming.bookhub.application.service;

import com.hamming.bookhub.infrastructure.request.users.RegistrationNewUserRequest;
import com.hamming.bookhub.infrastructure.request.users.UpdateUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse registerUser(RegistrationNewUserRequest request);
    UserResponse getUserById(UUID id);
    UserResponse updateUser(UUID id, UpdateUserRequest request);
    void deleteUser(UUID id);

}
