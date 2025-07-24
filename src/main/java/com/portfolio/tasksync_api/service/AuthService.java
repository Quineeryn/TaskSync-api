package com.portfolio.tasksync_api.service;

import com.portfolio.tasksync_api.dto.AuthResponse;
import com.portfolio.tasksync_api.dto.RegisterRequest;
import com.portfolio.tasksync_api.dto.UserResponse;
import com.portfolio.tasksync_api.model.User;
import com.portfolio.tasksync_api.repository.UserRepository;
import com.portfolio.tasksync_api.security.JwtUtil; // Import JwtUtil
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Inject JwtUtil

    public AuthResponse register(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser);

        UserResponse userResponse = UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();

        return AuthResponse.builder()
                .user(userResponse)
                .token(token)
                .build();
    }
}