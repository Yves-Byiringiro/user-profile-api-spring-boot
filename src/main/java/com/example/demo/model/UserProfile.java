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

    @NotBlank(message = "Username is needed")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Name is needed")
    private String name;

    @NotBlank(message = "Email address is needed")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Date of birth is needed")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Column(nullable = false)
    private UserProfileStatus status = UserProfileStatus.ACTIVE;

    @Transient
    private Integer age;

    @NotBlank(message = "Password is needed")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
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
