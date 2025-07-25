package com.portfolio.tasksync_api.controller;

import com.portfolio.tasksync_api.dto.ApiResponse;
import com.portfolio.tasksync_api.dto.AuthResponse;
import com.portfolio.tasksync_api.dto.LoginRequest;
import com.portfolio.tasksync_api.dto.RegisterRequest;
import com.portfolio.tasksync_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth") // Menambahkan versioning /v1
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);

        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .status("success")
                .data(authResponse)
                .message("User registered successfully")
                .build();

        // Menggunakan builder pattern untuk membuat respons yang lengkap
        return ResponseEntity
                .status(HttpStatus.CREATED) // Set status 201 Created
                .contentType(MediaType.APPLICATION_JSON) // Set header Content-Type
                .body(apiResponse); // Set body
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);

        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .status("success")
                .data(authResponse)
                .message("Login successful")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}