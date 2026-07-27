package com.example.app.service.impl;

import com.example.app.dto.user.UserRegistrationRequestDto;
import com.example.app.dto.user.UserResponseDto;
import com.example.app.exception.RegistrationException;
import com.example.app.mapper.UserMapper;
import com.example.app.model.User;
import com.example.app.repository.user.UserRepository;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("User with such email already exists. Email - "
                    + request.getEmail());
        }
        User user = userMapper.toModel(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
