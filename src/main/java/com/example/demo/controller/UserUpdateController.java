package com.example.demo.controller;

import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserUpdateForm;
import com.example.demo.service.UserUpdateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserUpdateController {
    private final UserUpdateService userUpdateService;

    @GetMapping("/userupdate")
    public String showUserUpdateForm(Model model) {
        UserInfo currentUser = userUpdateService.getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("userUpdateForm", new UserUpdateForm(currentUser));
        } else {
            model.addAttribute("userUpdateForm", new UserUpdateForm());
        }
        return "userupdateForm";
    }

    @PostMapping("/userupdate")
    public String processUserUpdateForm(@Valid @ModelAttribute UserUpdateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("バリデーションエラー: {}", result.getAllErrors());
            model.addAttribute("userUpdateForm", form);
            return "userupdateForm";
        }

        // プロファイル画像をバイト配列に変換
        MultipartFile profImg = form.getProf_img();
        if (profImg != null && !profImg.isEmpty()) {
            try {
                form.setProfImgBytes(profImg.getBytes());
                String base64Image = Base64.getEncoder().encodeToString(profImg.getBytes());
                model.addAttribute("base64Image", base64Image);
                log.debug("プロファイル画像をバイト配列に変換しました (サイズ: {} バイト)", form.getProfImgBytes().length);
            } catch (Exception e) {
                log.error("プロファイル画像のバイト配列への変換に失敗しました", e);
            }
        } else {
            log.debug("プロファイル画像が受信されませんでした");
        }

        model.addAttribute("userUpdateForm", form);
        return "userupdateConfirm";
    }

    @PostMapping("/userupdate/confirm")
    public String updateUser(@Valid @ModelAttribute UserUpdateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("確認時のバリデーションエラー: {}", result.getAllErrors());
            model.addAttribute("userUpdateForm", form);
            return "userupdateConfirm";
        }
        try {
            userUpdateService.updateUser(form);
            log.debug("ユーザー情報を正常に更新しました。成功ページにリダイレクトします");
            return "redirect:/userupdate/success";
        } catch (Exception e) {
            log.error("ユーザー情報の更新エラー", e);
            model.addAttribute("userUpdateError", "ユーザー情報の更新に失敗しました。もう一度お試しください。");
            model.addAttribute("userUpdateForm", form);
            return "userupdateConfirm";
        }
    }

    @GetMapping("/userupdate/success")
    public String showSuccessPage() {
        return "userupdateSuccess";
    }
}
