package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserSettingForm;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.UserSettingService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserSettingController {

    private final UserSettingService userSettingService;
    private final UserInfoRepository userInfoRepository;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/usersetting")
    public String showUserSettingForm(Model model) {
        String loginId = (String) session.getAttribute("loginId");
        UserInfo user = userInfoRepository.findById(loginId).orElse(null);
        if (user != null) {
            UserSettingForm form = new UserSettingForm();
            form.setEmail(user.getEmail());
            model.addAttribute("userSettingForm", form);
        } else {
            model.addAttribute("errorMessage", "ユーザー情報が見つかりません。");
        }
        return "usersettingForm";
    }

    @PostMapping("/usersetting")
    public String processUserSettingForm(@Valid @ModelAttribute UserSettingForm form, BindingResult result, 
                                         Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "usersettingForm";
        }

        try {
            String loginId = (String) session.getAttribute("loginId");
            userSettingService.updateUserSettings(loginId, form);
            redirectAttributes.addFlashAttribute("successMessage", "更新に成功しました。");
            return "redirect:/userdashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "更新に失敗しました。再試行してください。");
            return "usersettingForm";
        }
    }
}
