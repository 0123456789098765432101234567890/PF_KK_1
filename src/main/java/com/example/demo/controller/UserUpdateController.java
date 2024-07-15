package com.example.demo.controller;

import java.util.Base64;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserUpdateForm form = userUpdateService.getUserUpdateForm(username);
        model.addAttribute("userUpdateForm", form);
        return "userupdateForm";
    }

    @PostMapping("/userupdate")
    public String processUserUpdateForm(@Valid @ModelAttribute UserUpdateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("Validation errors: {}", result.getAllErrors());
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
                log.debug("Converted profile image to byte array (size: {} bytes)", form.getProfImgBytes().length);
            } catch (Exception e) {
                log.error("Failed to convert profile image to byte array", e);
            }
        } else {
            log.debug("No profile image received");
        }

        model.addAttribute("userUpdateForm", form);
        log.debug("Redirecting to confirmation page with form: {}", form.getUserName());
        return "userupdateConfirm";
    }

    @PostMapping("/userupdate/confirm")
    public String confirmUserUpdate(@Valid @ModelAttribute UserUpdateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("Validation errors on confirmation: {}", result.getAllErrors());
            model.addAttribute("userUpdateForm", form);
            return "userupdateConfirm";
        }
        try {
            userUpdateService.updateUser(form);
            log.debug("User successfully updated, redirecting to success page");
            return "redirect:/userupdate/success";
        } catch (Exception e) {
            log.error("Error confirming user update", e);
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
