package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAddForm {
    @NotEmpty(message = "Username is required")
    @Size(max = 255, message = "Username must be at most 255 characters")
    private String user_name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must be at most 255 characters")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 3, max = 32, message = "Password must be between 3 and 32 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Password can only contain alphanumeric characters, dashes, and underscores")
    private String pass;

    @NotEmpty(message = "Status is required")
    private String status; // "ALLOWED" or "DENIED"

    private MultipartFile prof_img; // プロフィール画像
    private byte[] profImgBytes; // プロフィール画像のバイト配列

    @Size(max = 255, message = "Username kana must be at most 255 characters")
    private String user_name_kana;

    private String gender; // "男性", "女性", "その他"

    @Max(value = 999, message = "Age must be a valid number and at most 3 digits")
    private Integer age;

    @Size(max = 1500, message = "Self introduction must be at most 1500 characters")
    private String self_intro;

    @NotEmpty(message = "Role is required")
    private String roles; // "ADMIN" or "USER"
}
