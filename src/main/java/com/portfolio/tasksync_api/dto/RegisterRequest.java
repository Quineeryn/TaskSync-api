package com.portfolio.tasksync_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern; // Import Pattern
import jakarta.validation.constraints.Size;   // Import Size
import lombok.Data;

@Data
public class RegisterRequest {

    @NotEmpty(message = "Nama tidak boleh kosong")
    private String name;

    @NotEmpty(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    // Menambahkan validasi password sesuai requirement
    @NotEmpty(message = "Password tidak boleh kosong")
    @Size(min = 8, message = "Password minimal harus 8 karakter")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password harus mengandung setidaknya satu huruf kecil, satu huruf besar, satu angka, dan satu simbol"
    )
    private String password;
}