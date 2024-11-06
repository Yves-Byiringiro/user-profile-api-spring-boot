package com.example.demo.service;

import com.example.demo.config.JwtService;
import com.example.demo.exception.*;
import com.example.demo.model.UserProfile;
import com.example.demo.payload.ApiResponse;
import com.example.demo.security.dtos.*;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.enums.UserProfileStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public List<UserProfileResponseDTO> getProfiles() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();

        List<UserProfileResponseDTO> responseDTOs = userProfiles.stream()
                .map(profile -> new UserProfileResponseDTO(
                        profile.getId(),
                        profile.getName(),
                        profile.getUsername(),
                        profile.getEmail(),
                        profile.getDob(),
                        profile.getAge(),
                        profile.getStatus()
                ))
                .toList();
        return responseDTOs;
    }

    public ApiResponse<UserProfileResponseDTO> getProfile(Long userProfileId) {
        UserProfile profile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new HandleException("profile with id " + userProfileId + " does not exists"));

        UserProfileResponseDTO dto = new UserProfileResponseDTO();
        dto.setId(profile.getId());
        dto.setName(profile.getName());
        dto.setUsername(profile.getUsername());
        dto.setEmail(profile.getEmail());
        dto.setDob(profile.getDob());
        dto.setAge(profile.getAge());
        dto.setStatus(profile.getStatus());

        return new ApiResponse<>(dto, "200", HttpStatus.OK);
    }

    @Transactional
    public ApiResponse<UserProfileResponseDTO>  updateUserProfile(Long userProfileId, UserProfileUpdateDTO request) {
        UserProfile userProfile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new HandleException("profile with id " + userProfileId + " does not exists"));

        if (request.getName() != null && !request.getName().isEmpty() && !Objects.equals(userProfile.getName(), request.getName())) {
            userProfile.setName(request.getName());
        }

        if (request.getDob() != null && !Objects.equals(userProfile.getDob(), request.getDob())) {
            userProfile.setDob(request.getDob());
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty() && !Objects.equals(userProfile.getEmail(), request.getEmail())) {
           Optional<UserProfile> userProfileOptional = userProfileRepository
                   .findUserProfileByEmail(request.getEmail());

           if (userProfileOptional.isPresent()) {
               throw new HandleException("email already taken");
           }
            userProfile.setEmail(request.getEmail());
        }

        if (request.getUsername() != null && !request.getUsername().isEmpty() && !Objects.equals(userProfile.getUsername(), request.getUsername())) {
            Optional<UserProfile> userProfileOptional = userProfileRepository
                    .findUserProfileByUsername(request.getUsername());

            if (userProfileOptional.isPresent()) {
                throw new HandleException("username already taken");
            }
            userProfile.setEmail(request.getEmail());
        }

        UserProfileResponseDTO dto = new UserProfileResponseDTO();
        dto.setId(userProfile.getId());
        dto.setName(userProfile.getName());
        dto.setUsername(userProfile.getUsername());
        dto.setEmail(userProfile.getEmail());
        dto.setDob(userProfile.getDob());
        dto.setAge(userProfile.getAge());

        return new ApiResponse<>(dto, "profile updated successfully", "200", HttpStatus.OK);

    }

    @Transactional
    public ApiResponse<UserProfileResponseDTO>  updateUserProfileStatus(Long userProfileId, String status) {
        UserProfileStatus userProfileStatus;

        try {
            userProfileStatus = UserProfileStatus.valueOf(status.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new HandleException("Invalid status");
        }

        UserProfile userProfile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new HandleException("profile with id " + userProfileId + " does not exists"));

        userProfile.setStatus(userProfileStatus);

        UserProfileResponseDTO dto = new UserProfileResponseDTO();
        dto.setId(userProfile.getId());
        dto.setName(userProfile.getName());
        dto.setUsername(userProfile.getUsername());
        dto.setEmail(userProfile.getEmail());
        dto.setDob(userProfile.getDob());
        dto.setAge(userProfile.getAge());
        dto.setStatus(userProfile.getStatus());
        return new ApiResponse<>(dto, "profile status updated successfully", "200", HttpStatus.OK);
    }


    public ApiResponse<Void> deleteUserProfile(Long userProfileId) {
        UserProfile userProfile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new HandleException("profile with id " + userProfileId + " does not exists"));

        userProfileRepository.deleteById(userProfileId);

        return new ApiResponse<>(
                "profile deleted successfully",
                "200",
                HttpStatus.OK
        );
    }

    @Transactional
    public ApiResponse<Void> changePassword(Long userProfileId, ChangePasswordDTO request) {
        UserProfile userProfile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new HandleException("profile with id " + userProfileId + " does not exists"));

        if(!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new HandleException("Password password do not match");
        }
        if (passwordEncoder.matches(request.getNewPassword(), userProfile.getPassword())) {
            throw new HandleException("New password cannot be the same as the old password. Please choose a different password");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), userProfile.getPassword())) {
            throw new HandleException("Old password is incorrect");
        }
        userProfile.setPassword(passwordEncoder.encode(request.getNewPassword()));

        return new ApiResponse<>(
                "Password changed successfully",
                "200",
                HttpStatus.OK
        );
    }
}

