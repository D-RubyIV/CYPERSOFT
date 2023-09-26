package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninAuthDto {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "username is required")
    private String password;
}
