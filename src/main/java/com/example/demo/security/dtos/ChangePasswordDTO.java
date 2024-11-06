package com.example.demo.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    @NotBlank(message = "Old password is required")
    @JsonProperty("old_password")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "New password must be at least 6 characters long")
    @JsonProperty("new_password")
    private String newPassword;

    @NotBlank(message = "Password confirmation is required")
    @JsonProperty("confirm_password")
    private String confirmPassword;
}






