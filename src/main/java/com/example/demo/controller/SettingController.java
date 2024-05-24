package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.SettingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping("/setting")
    public String showSetting(Model model) {
        UserInfo currentUser = settingService.getCurrentUserInfo();
        model.addAttribute("userInfo", currentUser);
        return "setting"; // ビュー名を "setting" に変更
    }

    @PostMapping("/setting")
    public String updateSetting(@Valid @ModelAttribute("userInfo") UserInfo userInfo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "setting"; // エラーがある場合は再度フォームを表示
        }
        settingService.updateUserInfo(userInfo);
        model.addAttribute("successMessage", "Settings updated successfully");
        return "redirect:/setting"; // 更新後に再び設定画面にリダイレクト
    }
}
