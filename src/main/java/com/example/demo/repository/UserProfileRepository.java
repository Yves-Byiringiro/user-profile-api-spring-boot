package com.example.demo.repository;

import com.example.demo.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT u FROM UserProfile u WHERE u.email = ?1")
    Optional<UserProfile> findUserProfileByEmail(String email);

    @Query("SELECT u FROM UserProfile u WHERE u.username = ?1")
    Optional<UserProfile> findUserProfileByUsername(String username);


}
