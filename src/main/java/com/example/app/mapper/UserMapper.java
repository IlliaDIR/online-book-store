package com.example.app.mapper;

import com.example.app.config.UserMapperConfig;
import com.example.app.dto.user.UserRegistrationRequestDto;
import com.example.app.dto.user.UserResponseDto;
import com.example.app.model.User;
import org.mapstruct.Mapper;

@Mapper(config = UserMapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userDto);
}
