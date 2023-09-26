package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpAuthDto {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "rePassword is required")
    private String rePassword;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "phone is required")
    private String phone;
}
