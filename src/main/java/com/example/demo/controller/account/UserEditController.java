package com.example.demo.controller.account;

import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserEditForm;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.account.UserEditService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("userEditForm")
public class UserEditController {

    private final UserInfoService userInfoService;
    private final UserEditService userEditService;

    // 編集フォームの表示
    @GetMapping("/useredit/{loginId}")
    public String showEditForm(@PathVariable String loginId, Model model) {
        // ユーザー情報を取得
        UserInfo user = userInfoService.findByLoginId(loginId);
        if (user == null) {
            log.error("User with loginId: {} not found", loginId);
            return "error";
        }

        // フォームにユーザー情報を設定
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
        form.setProfImgBytes(user.getProfImg());
        
        // プロフィール画像を表示するためのBase64エンコード
        if (user.getProfImg() != null) {
            String base64Image = Base64.getEncoder().encodeToString(user.getProfImg());
            model.addAttribute("base64Image", base64Image);
        }

        // モデルにフォームを設定
        model.addAttribute("userEditForm", form);
        return "usereditForm";
    }

    // 編集内容の確認
    @PostMapping("/useredit/confirm")
    public String confirmEdit(@Valid @ModelAttribute("userEditForm") UserEditForm form, BindingResult result, Model model) {
        // バリデーションエラーがあれば編集フォームに戻る
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "usereditForm";
        }

        // プロフィール画像をバイト配列に変換して設定
        MultipartFile profImg = form.getProf_img();
        if (profImg != null && !profImg.isEmpty()) {
            try {
                form.setProfImgBytes(profImg.getBytes());
                log.debug("Converted profile image to byte array (size: {} bytes)", form.getProfImgBytes().length);
            } catch (Exception e) {
                log.error("Failed to convert profile image to byte array", e);
            }
        }

        // プロフィール画像のBase64エンコード
        if (form.getProfImgBytes() != null) {
            String base64Image = Base64.getEncoder().encodeToString(form.getProfImgBytes());
            model.addAttribute("base64Image", base64Image);
        }

        // 確認画面に移行
        model.addAttribute("userEditForm", form);
        return "usereditConfirm";
    }

    // 編集内容の登録
    @PostMapping("/useredit/submit")
    public String submitEdit(@Valid @ModelAttribute("userEditForm") UserEditForm form, BindingResult result, SessionStatus sessionStatus) {
        // バリデーションエラーがあれば再度確認画面に戻る
        if (result.hasErrors()) {
            log.error("Validation errors on submit: {}", result.getAllErrors());
            return "usereditConfirm";
        }

        try {
            // ユーザー情報の更新
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
