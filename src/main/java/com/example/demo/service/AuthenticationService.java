package com.example.demo.service;

import com.example.demo.config.JwtService;
import com.example.demo.exception.*;
import com.example.demo.model.UserProfile;
import com.example.demo.payload.AuthenticationResponse;
import com.example.demo.security.dtos.*;
import com.example.demo.repository.UserProfileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse login(LoginDTO request) {
        try {
            if (StringUtils.isBlank(request.getUsername()) || StringUtils.isBlank(request.getPassword())) {
                throw new HandleException("Username and password are required");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            var userProfile = repository.findUserProfileByUsername(request.getUsername())
                    .orElseThrow(() -> new HandleException(IResponseMessage.BAD_CREDENTIALS));


            var jwtToken = jwtService.generateToken(userProfile);
            return AuthenticationResponse.builder()
                    .id(userProfile.getId())
                    .username(userProfile.getUsername())
                    .email(userProfile.getEmail())
                    .token(jwtToken)
                    .build();

        } catch (BadCredentialsException e) {
            throw new HandleException(IResponseMessage.BAD_CREDENTIALS);
        }
    }

    public AuthenticationResponse register(@Valid RegisterDTO request) {

        Optional<UserProfile> userProfileUsernameOptional = repository
                .findUserProfileByUsername(request.getUsername());

        if(userProfileUsernameOptional.isPresent()) {
            throw new HandleException("username already taken");
        }

        Optional<UserProfile> userProfileEmailOptional = repository
                .findUserProfileByEmail(request.getEmail());
        if(userProfileEmailOptional.isPresent()) {
            throw new HandleException("email already taken");
        }

        var userProfile = UserProfile.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .dob(request.getDob())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        repository.save(userProfile);
        var jwtToken = jwtService.generateToken(userProfile);
        return AuthenticationResponse.builder()
                .id(userProfile.getId())
                .username(userProfile.getUsername())
                .email(userProfile.getEmail())
                .token(jwtToken)
                .build();
    }
}

