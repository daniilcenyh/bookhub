package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.service.UserService;
import com.hamming.bookhub.infrastructure.request.users.RegistrationNewUserRequest;
import com.hamming.bookhub.infrastructure.request.users.UpdateUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * @param request
     * @return
     */
    @Override
    public UserResponse registerUser(RegistrationNewUserRequest request) {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public UserResponse getUserById(UUID id) {
        return null;
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        return null;
    }

    /**
     * @param id
     */
    @Override
    public void deleteUser(UUID id) {

    }
}
