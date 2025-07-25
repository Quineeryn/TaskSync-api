package com.portfolio.tasksync_api.exception;

import com.portfolio.tasksync_api.dto.ErrorDetails;
import com.portfolio.tasksync_api.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                "EMAIL_ALREADY_EXISTS",
                ex.getMessage(),
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
        );
        ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        // 409 Conflict adalah status yang tepat untuk resource yang sudah ada
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // Set status code
                .contentType(MediaType.APPLICATION_JSON) // Set content type
                .body(errorResponse); // Set body
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                "INVALID_CREDENTIALS",
                "Email atau password salah.", // Pesan yang ramah pengguna
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
        );
        ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);

        // 401 Unauthorized adalah status yang tepat untuk login yang gagal
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}