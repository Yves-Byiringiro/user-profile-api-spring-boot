package com.example.demo.security.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String name;
    private String username;
    private String email;
    private LocalDate dob;
    private String password;
}
