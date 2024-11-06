package com.example.demo.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    @NotBlank(message = "Old password is required")
    @JsonProperty("old_password")
    private String oldPassword;


    @JsonProperty("new_password")
    @NotBlank(message = "New Password is required")
    @Size(min = 6, message = "New Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "New Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String newPassword;


    @JsonProperty("confirm_password")
    @NotBlank(message = "Confirm Password is required")
    @Size(min = 6, message = "Confirm Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Confirm Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String confirmPassword;
}

