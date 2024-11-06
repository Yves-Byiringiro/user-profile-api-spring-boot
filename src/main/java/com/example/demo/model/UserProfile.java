package com.example.demo.model;

import com.example.demo.security.enums.UserProfileStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="profiles")
public class UserProfile implements UserDetails {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String username;

    private String name;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Column(nullable = false)
    private UserProfileStatus status = UserProfileStatus.ACTIVE;

    @Transient
    private Integer age;
    private String password;


    public UserProfile(long id, String username, String name, String email, LocalDate dob, String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.status = UserProfileStatus.ACTIVE;
        this.password = password;
    }

    public UserProfile(String username, String name, String email, LocalDate dob, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.status = UserProfileStatus.ACTIVE;
        this.password = password;
    }

    public UserProfile(String username, String name, String email, LocalDate dob) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.status = UserProfileStatus.ACTIVE;
    }

    public Integer getAge() {
        if (dob == null) {
            return null;
        }
        return Period.between(dob, LocalDate.now()).getYears();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
