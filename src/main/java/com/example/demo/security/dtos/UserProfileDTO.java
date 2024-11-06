package com.example.demo.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private long id;
    private String username;
    private String name;
    private String email;
    private LocalDate dob;
}
