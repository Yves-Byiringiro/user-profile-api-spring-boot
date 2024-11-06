package com.example.demo.service;

import com.example.demo.config.JwtService;
import com.example.demo.exception.HandleException;
import com.example.demo.model.UserProfile;
import com.example.demo.payload.AuthenticationResponse;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.dtos.LoginDTO;
import com.example.demo.security.dtos.RegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserProfileRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccessful() {
        LoginDTO loginRequest = new LoginDTO("Hervine", "12849Yves!");
        UserProfile userProfile = UserProfile.builder()
                .id(1L)
                .username("Hervine")
                .email("hervine@gmail.com")
                .build();

        when(repository.findUserProfileByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(userProfile));
        when(jwtService.generateToken(any(UserProfile.class))).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIZXJ2aW5lIiwiaWF0IjoxNzMwOTE2ODExLCJleHAiOjE3MzEwMDMyMTF9.V4F1PydTxTzGVnfVekLU2aRfQRnT7KOAbm3Eyk7kaWc");

        AuthenticationResponse response = authenticationService.login(loginRequest);

        assertEquals("Hervine", response.getUsername());
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIZXJ2aW5lIiwiaWF0IjoxNzMwOTE2ODExLCJleHAiOjE3MzEwMDMyMTF9.V4F1PydTxTzGVnfVekLU2aRfQRnT7KOAbm3Eyk7kaWc", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testLoginWithBadCredentialsThrowsException() {
        LoginDTO loginRequest = new LoginDTO("Hervine", "12859Bves!");

        when(repository.findUserProfileByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.empty());

        assertThrows(HandleException.class, () -> authenticationService.login(loginRequest));
    }

    @Test
    public void testRegisterSuccessful() {
        RegisterDTO registerRequest = new RegisterDTO("Hervine", "Hervine", "hervine@gmail.com", LocalDate.parse("1998-02-06"), "12849Yves!");
        UserProfile userProfile = UserProfile.builder()
                .id(1L)
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .build();

        when(repository.findUserProfileByUsername(registerRequest.getUsername())).thenReturn(Optional.empty());
        when(repository.findUserProfileByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("$2a$10$Cx6wsnJlhkwkru3vqzJa3.uuBRnKe0h3RjEqpa9F2g/X46Qmw8Wv6");
        when(jwtService.generateToken(any(UserProfile.class))).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIZXJ2aW5lIiwiaWF0IjoxNzMwOTE2ODExLCJleHAiOjE3MzEwMDMyMTF9.V4F1PydTxTzGVnfVekLU2aRfQRnT7KOAbm3Eyk7kaWc");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals("Hervine", response.getUsername());
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIZXJ2aW5lIiwiaWF0IjoxNzMwOTE2ODExLCJleHAiOjE3MzEwMDMyMTF9.V4F1PydTxTzGVnfVekLU2aRfQRnT7KOAbm3Eyk7kaWc", response.getToken());
        verify(repository).save(any(UserProfile.class));
    }

    @Test
    public void testRegisterWithExistingUsernameThrowsException() {
        RegisterDTO registerRequest = new RegisterDTO("Hervine", "Hervine", "hervine@gmail.com", LocalDate.parse("1998-02-06"), "12849Yves!");
        when(repository.findUserProfileByUsername(registerRequest.getUsername()))
                .thenReturn(Optional.of(new UserProfile()));

        assertThrows(HandleException.class, () -> authenticationService.register(registerRequest));
    }
}
