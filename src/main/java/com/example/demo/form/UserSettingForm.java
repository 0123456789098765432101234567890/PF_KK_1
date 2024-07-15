package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSettingForm {
    @Email(message = "正しいメールアドレスを入力してください。")
    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください。")
    @NotBlank(message = "メールアドレスを入力してください。")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,32}$", message = "パスワードは3〜32文字の半角英数字と_-のみ許可されます。")
    @NotBlank(message = "パスワードを入力してください。")
    private String pass;
}
