package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.security.dtos.*;
import com.example.demo.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/")
@RequiredArgsConstructor
@Validated
public class UserProfileController {
    private final UserProfileService userProfileService;


    @GetMapping(path = "user-profiles")
    public List<UserProfileResponseDTO> getProfiles() {
        return userProfileService.getProfiles();
    }

    @GetMapping(path = "user-profile/{userProfileId}")
    public ResponseEntity<ApiResponse<UserProfileResponseDTO>> getProfile(@PathVariable("userProfileId") Long userProfileId) {
        ApiResponse<UserProfileResponseDTO> response = userProfileService.getProfile(userProfileId);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "update-profile/{userProfileId}")
    public ResponseEntity<ApiResponse<UserProfileResponseDTO>> updateUserProfile(@PathVariable("userProfileId") Long userProfileId, @RequestBody UserProfileUpdateDTO userProfileUpdateDTO) {
        ApiResponse<UserProfileResponseDTO> response = userProfileService.updateUserProfile(userProfileId, userProfileUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "update-profile-status/{userProfileId}")
    public ResponseEntity<ApiResponse<UserProfileResponseDTO>> updateUserProfileStatus(@PathVariable("userProfileId") Long userProfileId, @Valid @RequestBody UserProfileUpdateStatusDTO status) {
        ApiResponse<UserProfileResponseDTO> response = userProfileService.updateUserProfileStatus(userProfileId, status.getStatus());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "delete-profile/{userProfileId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserProfile(@PathVariable("userProfileId") Long userProfileId) {
        ApiResponse<Void> response =  userProfileService.deleteUserProfile(userProfileId);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "change-password/{userProfileId}")
    public ResponseEntity<ApiResponse<Void>>  updateUserProfileStatus(@PathVariable("userProfileId") Long userProfileId, @Valid @RequestBody ChangePasswordDTO changePasswordRequest) {
        ApiResponse<Void> response = userProfileService.changePassword(userProfileId, changePasswordRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "search-profile/{keywordToSearch}")
    public ResponseEntity<List<UserProfileResponseDTO>> getProfileBySearch(@PathVariable("keywordToSearch") String keywordToSearch) {
        List<UserProfileResponseDTO> response = userProfileService.search(keywordToSearch);
        return ResponseEntity.ok(response);
    }
}
