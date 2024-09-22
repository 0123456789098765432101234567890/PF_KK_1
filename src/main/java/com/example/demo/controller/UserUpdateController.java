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

        // プロフィール画像を表示するためのBase64エンコード
        if (form.getProfImgBytes() != null) {
            String base64Image = Base64.getEncoder().encodeToString(form.getProfImgBytes());
            model.addAttribute("base64Image", base64Image);
        }

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

        model.addAttribute("userUpdateForm", form);
        log.debug("Redirecting to confirmation page with form: {}", form.getUserName());
        return "userupdateConfirm";
    }
}
