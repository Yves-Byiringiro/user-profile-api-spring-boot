package com.example.demo.payload;


import lombok.*;

@Data
@Builder
public class AuthenticationResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
}
