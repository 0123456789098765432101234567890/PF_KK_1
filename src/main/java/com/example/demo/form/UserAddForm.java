package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.validation.FileSize;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAddForm {

    public interface AdminValidation {}
    public interface UserValidation {}

    @NotEmpty(message = "ユーザー名は必須項目です", groups = {UserValidation.class, AdminValidation.class})
    @Size(max = 255, message = "ユーザー名は255文字以内で入力してください", groups = {UserValidation.class, AdminValidation.class})
    private String user_name;

    @NotEmpty(message = "メールアドレスは必須項目です", groups = {UserValidation.class, AdminValidation.class})
    @Email(message = "メールアドレスの形式が正しくありません", groups = {UserValidation.class, AdminValidation.class})
    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください", groups = {UserValidation.class, AdminValidation.class})
    private String email;

    @NotEmpty(message = "パスワードは必須項目です", groups = {UserValidation.class, AdminValidation.class})
    @Size(min = 3, max = 32, message = "パスワードは3文字以上32文字以内で入力してください", groups = {UserValidation.class, AdminValidation.class})
    private String pass;

    @NotEmpty(message = "ステータスは必須項目です", groups = {UserValidation.class, AdminValidation.class})
    private String status; // "ALLOWED" or "DENIED"

    @NotNull(message = "プロフィール画像を選択してください")
    @FileSize(maxSize = 2 * 1024 * 1024, message = "ファイルサイズが2MBを超えています。")
    private MultipartFile prof_img;

    private byte[] profImgBytes;

    @NotEmpty(message = "名前（ふりがな）は必須項目です", groups = UserValidation.class)
    @Size(max = 255, message = "名前（ふりがな）は255文字以内で入力してください", groups = UserValidation.class)
    @Pattern(regexp = "^[ぁ-んー]+$", message = "名前（ふりがな）はひらがなのみ使用できます", groups = UserValidation.class)
    private String user_name_kana;

    @NotNull(message = "性別は必須項目です", groups = {UserValidation.class})
    private String gender;

    @NotNull(message = "年齢は必須項目です")
    @Min(value = 0, message = "年齢は0以上の数値を入力してください")
    @Max(value = 999, message = "年齢は3桁以内の数値を入力してください")
    private Integer age;

    @Size(max = 1500, message = "自己紹介は1500文字以内で入力してください", groups = UserValidation.class)
    private String self_intro;

    @NotEmpty(message = "権限は必須項目です", groups = {UserValidation.class, AdminValidation.class})
    private String roles; // "ADMIN" or "USER"
}
