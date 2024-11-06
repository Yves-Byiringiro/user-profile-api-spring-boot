package com.example.demo.service;

import com.example.demo.exception.HandleException;
import com.example.demo.model.UserProfile;
import com.example.demo.security.dtos.ChangePasswordDTO;
import com.example.demo.security.dtos.UserProfileUpdateDTO;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.enums.UserProfileStatus;
import com.example.demo.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserProfileServiceTest {

    @Autowired
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock

    private UserProfile userProfile;

    @BeforeEach
    void setUp() {
        userProfile = new UserProfile();
        userProfile.setId(32L);
        userProfile.setUsername("Hervine");
        userProfile.setEmail("hervine@gmail.com");
        userProfile.setDob(LocalDate.of(1998, 2, 6));
        userProfile.setAge(26);
        userProfile.setStatus(UserProfileStatus.ACTIVE);
        userProfile.setPassword(passwordEncoder.encode("12849Yves!"));
    }


    @Test
    public void testGetProfile() {
        when(userProfileRepository.findById(32L)).thenReturn(Optional.of(userProfile));

        var response = userProfileService.getProfile(25L);

        assertNotNull(response);
        assertEquals("Hervine", response.getData().getName());
        assertEquals(HttpStatus.OK, response.getStatus());
    }



    @Test
    public void testUpdateUserProfile() {
        UserProfileUpdateDTO updateDTO = new UserProfileUpdateDTO();
        updateDTO.setName("HervinUwase");
        updateDTO.setEmail("waseherv@gmail.com");

        when(userProfileRepository.findById(32L)).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.findUserProfileByUsername("Hervine")).thenReturn(Optional.empty());

        var response = userProfileService.updateUserProfile(32L, updateDTO);

        assertNotNull(response);
        assertEquals("HervinUwase", response.getData().getName());
        assertEquals("newemail@gmail.com", response.getData().getEmail());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void testUpdateUserProfile_EmailTaken() {
        UserProfileUpdateDTO updateDTO = new UserProfileUpdateDTO();
        updateDTO.setEmail("hervine@gmail.com");

        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.findUserProfileByEmail("hervine@gmail.com")).thenReturn(Optional.of(userProfile));

        assertThrows(HandleException.class, () -> userProfileService.updateUserProfile(1L, updateDTO));
    }

    @Test
    public void testUpdateUserProfileStatus() {
        when(userProfileRepository.findById(25L)).thenReturn(Optional.of(userProfile));

        var response = userProfileService.updateUserProfileStatus(25L, "INACTIVE");

        assertNotNull(response);
        assertEquals(UserProfileStatus.INACTIVE, response.getData().getStatus());
        assertEquals(HttpStatus.OK, response.getStatus());
    }


    @Test
    public void testDeleteUserProfile() {
        when(userProfileRepository.findById(302L)).thenReturn(Optional.of(userProfile));

        var response = userProfileService.deleteUserProfile(302L);

        assertNotNull(response);
        assertEquals("profile deleted successfully", response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatus());
    }


    @Test
    public void testChangePassword() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("12849Yves!", "newpassWord123@", "newpassWord123@");

        when(userProfileRepository.findById(9L)).thenReturn(Optional.of(userProfile));
        when(passwordEncoder.matches("12849Yves!", userProfile.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newpassWord123@")).thenReturn("$2a$10$Cx6wsnJlhkwkru3vqzJa3.uuBRnKe0h3RjEqpa9F2g/X46Qmw8Wv6");

        var response = userProfileService.changePassword(9L, changePasswordDTO);

        assertNotNull(response);
        assertEquals("Password changed successfully", response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void testChangePassword_NewPasswordSameAsOld() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("12849Yves!", "12849Yves!", "12849Yves!");

        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile));

        assertThrows(HandleException.class, () -> userProfileService.changePassword(25L, changePasswordDTO));
    }




}
