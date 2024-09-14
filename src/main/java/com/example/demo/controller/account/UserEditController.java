package com.example.demo.controller.account;

import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserEditForm;
import com.example.demo.form.UserEditForm.AdminValidation;
import com.example.demo.form.UserEditForm.UserValidation;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.account.UserEditService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("userEditForm")
public class UserEditController {

    private final UserInfoService userInfoService;
    private final UserEditService userEditService;
    private final LocalValidatorFactoryBean validatorFactoryBean;

    // 編集フォームの表示
    @GetMapping("/useredit/{loginId}")
    public String showEditForm(@PathVariable String loginId, Model model) {
        // ユーザー情報をデータベースから取得
        UserInfo user = userInfoService.findByLoginId(loginId);
        if (user == null) {
            log.error("User with loginId: {} not found", loginId);
            return "error";
        }
        
        // フォームにユーザー情報をセット
        UserEditForm form = new UserEditForm();
        form.setLoginId(user.getLoginId());
        form.setUser_name(user.getUser_name());
        form.setEmail(user.getEmail());
        form.setPass(user.getPass());
        form.setStatus(user.getStatus());
        form.setRoles(user.getRoles());
        form.setUser_name_kana(user.getUserNameKana());
        form.setGender(user.getGender());
        form.setAge(user.getAge());
        form.setSelf_intro(user.getSelfIntro());

        // プロフィール画像が存在する場合
        if (user.getProfImg() != null) {
            form.setProfImgBytes(user.getProfImg());
            String base64Image = Base64.getEncoder().encodeToString(user.getProfImg());
            model.addAttribute("base64Image", base64Image);
        }
        
        // モデルにフォームを設定
        model.addAttribute("userEditForm", form);
        return "usereditForm";
    }

    // 編集内容の確認
    @PostMapping("/useredit/confirm")
    public String confirmEdit(@ModelAttribute("userEditForm") UserEditForm form, Model model, BindingResult result) {
        // 役割に応じたバリデーショングループを適用
        if ("ADMIN".equals(form.getRoles())) {
            // 管理者の場合は AdminValidation グループでバリデーション
            validatorFactoryBean.validate(form, result, AdminValidation.class);
        } else if ("USER".equals(form.getRoles())) {
            // 一般ユーザーの場合は UserValidation グループでバリデーション
            validatorFactoryBean.validate(form, result, UserValidation.class);
        }

        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "usereditForm";
        }

        // プロファイル画像をバイト配列に変換
        MultipartFile profImg = form.getProf_img();
        if (profImg != null && !profImg.isEmpty()) {
            try {
                form.setProfImgBytes(profImg.getBytes());
                log.debug("Converted profile image to byte array (size: {} bytes)", form.getProfImgBytes().length);
            } catch (Exception e) {
                log.error("Failed to convert profile image to byte array", e);
            }
        }

        // 確認画面にデータを表示
        model.addAttribute("userEditForm", form);
        if (form.getProfImgBytes() != null) {
            String base64Image = Base64.getEncoder().encodeToString(form.getProfImgBytes());
            model.addAttribute("base64Image", base64Image);
        }

        return "usereditConfirm";
    }

    // 編集内容の登録
    @PostMapping("/useredit/submit")
    public String submitEdit(@ModelAttribute("userEditForm") @Validated(UserValidation.class) UserEditForm form, BindingResult result, SessionStatus sessionStatus) {
        if (result.hasErrors()) {
            log.error("Validation errors on submit: {}", result.getAllErrors());
            return "usereditForm";
        }

        try {
            // ユーザー情報を更新
            userEditService.updateUser(form);
            sessionStatus.setComplete();
            log.debug("User successfully updated, redirecting to user list");
            return "redirect:/userlist";
        } catch (Exception e) {
            log.error("Error updating user", e);
            return "error";
        }
    }
}
