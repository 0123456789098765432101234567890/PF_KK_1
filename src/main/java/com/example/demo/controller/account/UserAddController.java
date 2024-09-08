package com.example.demo.controller.account;

import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.AdminAddForm;
import com.example.demo.form.UserAddForm;
import com.example.demo.service.account.UserAddService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserAddController {
    private final UserAddService userAddService;

    @GetMapping("/useradd")
    public String showUserAddForm(Model model) {
        if (!model.containsAttribute("userAddForm")) {
            model.addAttribute("userAddForm", new UserAddForm());
        }
        return "useraddForm";
    }

    @PostMapping("/useradd")
    public String processUserAddForm(@RequestParam("roles") String roles, @Valid @ModelAttribute UserAddForm userForm, 
                                     BindingResult userResult, @Valid @ModelAttribute AdminAddForm adminForm, 
                                     BindingResult adminResult, Model model) {

        if ("ADMIN".equals(roles)) {
            if (adminResult.hasErrors()) {
                model.addAttribute("adminAddForm", adminForm);
                return "useraddForm";
            }
            // 管理者登録処理（必要に応じて追加）
            return "useraddConfirm";
        } else {
            if (userResult.hasErrors()) {
                model.addAttribute("userAddForm", userForm);
                return "useraddForm";
            }

            if (userForm.getProf_img() != null && !userForm.getProf_img().isEmpty()) {
                try {
                    userForm.setProfImgBytes(userForm.getProf_img().getBytes());
                    String base64Image = Base64.getEncoder().encodeToString(userForm.getProf_img().getBytes());
                    model.addAttribute("base64Image", base64Image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "useraddConfirm";
        }
    }
}
