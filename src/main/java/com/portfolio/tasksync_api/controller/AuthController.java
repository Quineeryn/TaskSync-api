package com.portfolio.tasksync_api.controller;

import com.portfolio.tasksync_api.dto.ApiResponse;
import com.portfolio.tasksync_api.dto.AuthResponse;
import com.portfolio.tasksync_api.dto.RegisterRequest;
import com.portfolio.tasksync_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        // 1. Panggil service, yang sekarang mengembalikan AuthResponse
        AuthResponse authResponse = authService.register(request);

        // 2. Buat ApiResponse yang akan menjadi body dari respons
        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .status("success")
                .data(authResponse)
                .message("User registered successfully")
                .build();

        // 3. Kembalikan ResponseEntity dengan status 201 CREATED
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}