package com.example.demo.security.dtos;


import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateDTO {
    private String name;
    private String email;
    private LocalDate dob;
    private String username;
}
