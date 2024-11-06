package com.example.demo.repository;

import com.example.demo.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT u FROM UserProfile u WHERE u.email = ?1")
    Optional<UserProfile> findUserProfileByEmail(String email);

    @Query("SELECT u FROM UserProfile u WHERE u.username = ?1")
    Optional<UserProfile> findUserProfileByUsername(String username);

    @Query("SELECT u FROM UserProfile u WHERE " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UserProfile> searchByKeyword(@Param("keyword") String keyword);
}
