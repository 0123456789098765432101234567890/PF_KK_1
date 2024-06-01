package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UpdateForm;
import com.example.demo.service.SettingService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SettingController {
    private final SettingService settingService;

    @GetMapping("/update")
    public String showUpdateForm(Model model) {
        model.addAttribute("updateForm", new UpdateForm());
        return "updateForm";
    }

    @PostMapping("/update")
    public String updateUserInfo(@Valid UpdateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "updateForm";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName(); // ログインIDを取得

        try {
            log.debug("Updating user info for loginId: {}", loginId);
            // Emailの更新処理
            settingService.updateEmail(loginId, form.getEmail());
            // Passwordの更新処理
            settingService.updatePassword(loginId, form.getPass());
            log.debug("Update successful for loginId: {}", loginId);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed for loginId: {}", loginId, e);
            model.addAttribute("updateError", "パスワードの形式が正しくありません。");
            return "updateForm";
        } catch (Exception e) {
            log.error("Update failed for loginId: {}", loginId, e);
            model.addAttribute("updateError", "更新処理に失敗しました。");
            return "updateForm";
        }

        return "redirect:/update?success";
    }
}
