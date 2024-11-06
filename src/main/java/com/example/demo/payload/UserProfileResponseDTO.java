package com.example.demo.payload;

import com.example.demo.security.enums.UserProfileStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private LocalDate dob;
    private Integer age;
    private UserProfileStatus status;
}