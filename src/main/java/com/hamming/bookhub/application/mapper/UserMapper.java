package com.hamming.bookhub.application.mapper;

import com.hamming.bookhub.domain.model.entity.UserEntity;
import com.hamming.bookhub.infrastructure.request.users.RegistrationNewUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserEntity fromCreateNewUserRequestToUserEntity(RegistrationNewUserRequest request);
    UserResponse fromUserEntityToUserResponse(UserEntity entity);
}
