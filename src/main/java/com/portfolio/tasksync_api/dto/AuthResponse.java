package com.portfolio.tasksync_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private UserResponse user;
    private String token;
}