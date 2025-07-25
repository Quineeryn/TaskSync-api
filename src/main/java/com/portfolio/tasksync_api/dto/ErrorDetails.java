package com.portfolio.tasksync_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private String code;
    private String message;
    private String timestamp;
}