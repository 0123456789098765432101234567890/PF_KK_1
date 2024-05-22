package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "user_info")
@Data
public class UserInfo {
    
    @Id
    @Column(name = "login_id")
    @NotEmpty(message = "Login ID is required")
    private String loginid;
    
    @Column(name = "pass")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Password can only contain alphanumeric characters, dashes, and underscores")
    private String pass;
    
    @Column(name = "email")
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email should be at most 255 characters")
    private String email;
}
