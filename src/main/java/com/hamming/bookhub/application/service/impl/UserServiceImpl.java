package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.mapper.UserMapper;
import com.hamming.bookhub.application.repository.UserRepository;
import com.hamming.bookhub.application.service.UserService;
import com.hamming.bookhub.domain.exception.users.UserAlreadyExistsException;
import com.hamming.bookhub.domain.exception.users.UserNotFoundException;
import com.hamming.bookhub.domain.model.entity.UserEntity;
import com.hamming.bookhub.infrastructure.request.users.RegistrationNewUserRequest;
import com.hamming.bookhub.infrastructure.request.users.UpdateUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * @param request
     * @return
     */
    @Override
    @Transactional
    public UserResponse registerUser(RegistrationNewUserRequest request) {
        log.info("REGISTRATION_NEW_USER time: {}", LocalDateTime.now());
        if (validationUserFieldsIfExist(request.email(), request.username())) {
            log.warn("USER_ALREADY_EXISTS with USERNAME: {}, EMAIL: {}", request.username(), request.email());
            throw new UserAlreadyExistsException("USER_ALREADY_EXISTS with USERNAME: {%s}, EMAIL: {%s}.Time exception: {%s}".formatted(request.username(), request.email(), LocalDateTime.now()));
        }
        var entityToSave = userMapper.fromCreateNewUserRequestToUserEntity(request);

        var savedEntity = userRepository.save(entityToSave);
        log.info("SUCCESSFUL_REGISTRATION_USER with USERNAME: {}, EMAIL: {}", savedEntity.getUsername(), savedEntity.getEmail());
        return userMapper.fromUserEntityToUserResponse(savedEntity);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public UserResponse getUserById(UUID id) {
        log.info("FIND_USER_BY_UUID time: {}", LocalDateTime.now());
        var entity = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("USER_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(id, LocalDateTime.now()))
        );
        log.info("SUCCESSFUL_FOUNDED_USER with UUID: {}", id);
        return userMapper.fromUserEntityToUserResponse(entity);
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        if (!userRepository.existsById(id)) {
            log.warn("USER_NOT_FOUNDED with UUID: {}. Time exception: {}", id, LocalDateTime.now());
            throw new UserNotFoundException("USER_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(id, LocalDateTime.now()));
        }
        if (validationUserFieldsIfExist(request.email(), request.username())) {
            log.warn("USER_ALREADY_EXISTS with USERNAME: {}, EMAIL: {}", request.username(), request.email());
            throw new UserAlreadyExistsException("USER_ALREADY_EXISTS with USERNAME: {%s}, EMAIL: {%s}.Time exception: {%s}".formatted(request.username(), request.email(), LocalDateTime.now()));
        }

        var entityToUpdate = UserEntity.builder()
                .id(id)
                .email(request.email())
                .username(request.username())
                .build();

        var updatedEntity = userRepository.save(entityToUpdate);

        log.info("SUCCESSFUL_UPDATE_USER with UUID: {}", id);
        return userMapper.fromUserEntityToUserResponse(updatedEntity);
    }

    // TODO: сделать мягкое удаление через параметр состояния пользователя USER_STATE (ACTIVE, DELETED),
    //  для того что бы в комментариях к книге не выводились пользователи которые удалили свои аккаунты
    /**
     * @param id
     */
    @Override
    @Transactional
    public void deleteUser(UUID id) {
        log.info("DELETE_USER_BY_UUID time: {}", LocalDateTime.now());
        var entity = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("USER_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(id, LocalDateTime.now()))
        );

        userRepository.delete(entity);
        log.info("SUCCESSFUL_DELETED_USER with UUID: {}", id);
    }


    private boolean validationUserFieldsIfExist(String email, String username) {
        log.info("VALIDATION_IF_EXISTS_USERNAME_OR_EMAIL_REQUEST");
        return !userRepository.existsByUsername(username) && !userRepository.existsByEmail(email);
    }
}
