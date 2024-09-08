package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminAddForm {
    @NotEmpty(message = "名前は必須項目です")
    private String user_name;

    @NotEmpty(message = "メールアドレスは必須項目です")
    private String email;

    @NotEmpty(message = "パスワードは必須項目です")
    private String pass;
    
    @NotEmpty(message = "ステータスは必須項目です")
    private String status; // "ALLOWED" or "DENIED"

    private String roles = "ADMIN";  // 管理者のデフォルト値
}
