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
    @NotEmpty(message = "ユーザー名は必須項目です")
    @Size(max = 255, message = "ユーザー名は255文字以内で入力してください")
    private String user_name;

    @NotEmpty(message = "メールアドレスは必須項目です")
    @Email(message = "メールアドレスの形式が正しくありません")
    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください")
    private String email;

    @NotEmpty(message = "パスワードは必須項目です")
    @Size(min = 3, max = 32, message = "パスワードは3文字以上32文字以内で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "パスワードは英数字、ダッシュ、アンダースコアのみ使用できます")
    private String pass;

    @NotEmpty(message = "ステータスは必須項目です")
    private String status; // "ALLOWED" or "DENIED"

    private MultipartFile prof_img; // プロフィール画像
    private byte[] profImgBytes; // プロフィール画像のバイト配列

    @Size(max = 255, message = "名前（ふりがな）は255文字以内で入力してください")
    @Pattern(regexp = "^[ぁ-んー]+$", message = "名前（ふりがな）はひらがなのみ使用できます")
    private String user_name_kana;

    private String gender; // "男性", "女性", "その他"

    @Max(value = 999, message = "年齢は有効な数字で、3桁以内で入力してください")
    private Integer age;

    @Size(max = 1500, message = "自己紹介は1500文字以内で入力してください")
    private String self_intro;

    @NotEmpty(message = "権限は必須項目です")
    private String roles; // "ADMIN" or "USER"
}
