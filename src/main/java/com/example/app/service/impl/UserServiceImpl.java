package com.example.app.service.impl;

import com.example.app.dto.user.UserRegistrationRequestDto;
import com.example.app.dto.user.UserResponseDto;
import com.example.app.exception.EntityNotFoundException;
import com.example.app.exception.RegistrationException;
import com.example.app.mapper.UserMapper;
import com.example.app.model.Role;
import com.example.app.model.RoleName;
import com.example.app.model.User;
import com.example.app.repository.role.RoleRepository;
import com.example.app.repository.user.UserRepository;
import com.example.app.service.ShoppingCartService;
import com.example.app.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("User with such email already exists. Email - "
                    + request.getEmail());
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role =
                roleRepository.findByName(RoleName.USER).orElseThrow(() ->
                        new EntityNotFoundException(
                                "Role with name " + RoleName.USER + " not found"));
        user.setRoles(Set.of(role));
        User saved = userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        return userMapper.toDto(saved);
    }
}
