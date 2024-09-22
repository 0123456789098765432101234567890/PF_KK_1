package com.example.demo.form;

import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UserInfo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateForm {

    private String loginId;

    @Size(max = 255, message = "名前は255文字以内で入力してください")
    private String userName;

    @NotEmpty(message = "名前（ふりがな）は必須項目です")
    @Size(max = 255, message = "ふりがなは255文字以内で入力してください")
    @Pattern(regexp = "^[ぁ-ん]+$", message = "ふりがなはひらがなのみ入力可能です")
    private String userNameKana;

    @NotNull(message = "性別は必須項目です")
    private String gender; // "男性", "女性", "その他"

    @NotNull(message = "年齢は必須項目です")
    private Integer age;

    @Size(max = 1500, message = "自己紹介は1500文字以内で入力してください")
    private String selfIntro;

    private MultipartFile prof_img;
    private byte[] profImgBytes;

    // Base64エンコードされた画像の文字列を保持するフィールドを追加
    private String profImgBase64;

    public UserUpdateForm(UserInfo userInfo) {
        this.loginId = userInfo.getLoginId();
        this.userName = userInfo.getUser_name();
        this.userNameKana = userInfo.getUserNameKana();
        this.gender = userInfo.getGender();
        this.age = userInfo.getAge();
        this.selfIntro = userInfo.getSelfIntro();
        this.profImgBytes = userInfo.getProfImg();

        // プロフィール画像が存在する場合、Base64エンコードして設定
        if (this.profImgBytes != null && this.profImgBytes.length > 0) {
            this.profImgBase64 = Base64.getEncoder().encodeToString(this.profImgBytes);
        }
    }
}
