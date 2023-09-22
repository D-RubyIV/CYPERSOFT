package com.example.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "username is required")
    private String password;

    private String firstname;

    private String lastname;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "phone is required")
    private String phone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isEnable;

}
