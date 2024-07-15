package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UserInfo;

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
    
    @Size(max = 255, message = "ふりがなは255文字以内で入力してください")
    @Pattern(regexp = "^[ぁ-ん]+$", message = "ふりがなはひらがなのみ入力可能です")
    private String userNameKana;
    
    private String gender; // "男性", "女性", "その他"
    
    private Integer age;
    
    @Size(max = 1500, message = "自己紹介は1500文字以内で入力してください")
    private String selfIntro;
    
    private MultipartFile prof_img;
    private byte[] profImgBytes;

    public UserUpdateForm(UserInfo userInfo) {
        this.loginId = userInfo.getLoginId();
        this.userName = userInfo.getUser_name();
        this.userNameKana = userInfo.getUserNameKana();
        this.gender = userInfo.getGender();
        this.age = userInfo.getAge();
        this.selfIntro = userInfo.getSelfIntro();
        this.profImgBytes = userInfo.getProfImg();
    }
}
