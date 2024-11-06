package com.example.demo.security.dtos;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateStatusDTO {
    @NotBlank(message = "Status is required")
    private String status;
}
