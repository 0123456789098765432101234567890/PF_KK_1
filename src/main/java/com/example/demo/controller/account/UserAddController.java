package com.example.demo.controller.account;

import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.UserAddForm;
import com.example.demo.form.UserAddForm.AdminValidation;
import com.example.demo.form.UserAddForm.UserValidation;
import com.example.demo.service.account.UserAddService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserAddController {
    private final UserAddService userAddService;
    private final LocalValidatorFactoryBean validatorFactoryBean;

    @GetMapping("/useradd")
    public String showUserAddForm(Model model) {
        if (!model.containsAttribute("userAddForm")) {
            model.addAttribute("userAddForm", new UserAddForm());
        }
        return "useraddForm";
    }

    // 「確認」ボタンの処理
    @PostMapping("/useradd")
    public String processUserAddForm(@RequestParam("roles") String roles,
                                     @ModelAttribute("userAddForm") UserAddForm form,
                                     BindingResult result,
                                     Model model) {

        // rolesに応じてバリデーションを行う
        if ("ADMIN".equals(roles)) {
            // 管理者バリデーションを適用
            validatorFactoryBean.validate(form, result, AdminValidation.class);
        } else if ("USER".equals(roles)) {
            // 一般ユーザーバリデーションを適用
            validatorFactoryBean.validate(form, result, UserValidation.class);
        }

        // バリデーションエラーがある場合は入力フォームに戻る
        if (result.hasErrors()) {
            log.debug("Validation errors found: {}", result.getAllErrors());
            model.addAttribute("userAddForm", form);
            return "useraddForm";
        }

        // プロフィール画像を処理して確認画面へ表示
        if (form.getProf_img() != null && !form.getProf_img().isEmpty()) {
            try {
                form.setProfImgBytes(form.getProf_img().getBytes());
                String base64Image = Base64.getEncoder().encodeToString(form.getProf_img().getBytes());
                model.addAttribute("base64Image", base64Image);
            } catch (Exception e) {
                log.error("Failed to process profile image", e);
            }
        }

        // 確認画面に遷移
        model.addAttribute("userAddForm", form);
        return "useraddConfirm";
    }

    // 「登録」ボタンの処理
    @PostMapping("/useradd/register")
    public String registerUser(@RequestParam("roles") String roles,
                               @ModelAttribute("userAddForm") UserAddForm form,
                               BindingResult result,
                               Model model) {

        log.debug("Register button clicked");

        // rolesの重複を解消する（カンマ区切りで来た場合に対応）
        String[] roleArray = roles.split(",");
        String uniqueRole = roleArray[0]; // 最初の1つだけを使用
        log.debug("Unique role after deduplication: {}", uniqueRole);

        // フォームのrolesフィールドを明示的に設定
        form.setRoles(uniqueRole); // 重複を避けるため、最初の値のみをセット

        // バリデーションエラーがあれば再度確認画面を表示
        if (result.hasErrors()) {
            log.debug("Validation errors found: {}", result.getAllErrors());
            model.addAttribute("userAddForm", form);
            return "useraddConfirm";
        }

        try {
            log.debug("Attempting to add user to the database with role: {}", form.getRoles());
            userAddService.addUser(form);
            log.debug("User successfully added, redirecting to user list");
            return "redirect:/userlist?addSuccess=true";
        } catch (Exception e) {
            log.error("Error occurred during user registration", e);
            model.addAttribute("userAddError", "ユーザーの登録に失敗しました。再度お試しください。");
            model.addAttribute("userAddForm", form);
            return "useraddConfirm";
        }
    }
}
