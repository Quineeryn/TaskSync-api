package com.portfolio.tasksync_api.service;

import com.portfolio.tasksync_api.dto.AuthResponse;
import com.portfolio.tasksync_api.dto.LoginRequest;
import com.portfolio.tasksync_api.exception.EmailAlreadyExistsException;
import com.portfolio.tasksync_api.dto.RegisterRequest;
import com.portfolio.tasksync_api.dto.UserResponse;
import com.portfolio.tasksync_api.model.User;
import com.portfolio.tasksync_api.repository.UserRepository;
import com.portfolio.tasksync_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {

            throw new EmailAlreadyExistsException("Email " + request.getEmail() + " sudah terdaftar.");
        });

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

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User tidak ditemukan setelah autentikasi berhasil"));


        String token = jwtUtil.generateToken(user);


        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();


        return AuthResponse.builder()
                .user(userResponse)
                .token(token)
                .build();
    }
}