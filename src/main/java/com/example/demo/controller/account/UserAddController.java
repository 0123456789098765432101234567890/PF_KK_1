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
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserAddController {
    private final UserAddService userAddService;

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
                                     @Valid @ModelAttribute UserAddForm userForm, 
                                     BindingResult userResult, 
                                     @Valid @ModelAttribute AdminAddForm adminForm, 
                                     BindingResult adminResult, 
                                     Model model) {

        if ("ADMIN".equals(roles)) {
            if (adminResult.hasErrors()) {
                model.addAttribute("adminAddForm", adminForm);
                return "useraddForm";
            }
            model.addAttribute("userAddForm", adminForm);
            return "useraddConfirm";
        } else {
            if (userResult.hasErrors()) {
                model.addAttribute("userAddForm", userForm);
                return "useraddForm";
            }

            // プロフィール画像を処理して確認画面へ表示
            if (userForm.getProf_img() != null && !userForm.getProf_img().isEmpty()) {
                try {
                    userForm.setProfImgBytes(userForm.getProf_img().getBytes());
                    String base64Image = Base64.getEncoder().encodeToString(userForm.getProf_img().getBytes());
                    model.addAttribute("base64Image", base64Image);
                } catch (Exception e) {
                    log.error("Failed to process profile image", e);
                }
            }

            // 確認画面に遷移
            model.addAttribute("userAddForm", userForm);
            return "useraddConfirm";
        }
    }

    // 「登録」ボタンの処理
    @PostMapping("/useradd/register")
    public String registerUser(@RequestParam("roles") String roles, 
                               @Valid @ModelAttribute UserAddForm form, 
                               BindingResult result, 
                               Model model) {

        log.debug("Register button clicked");

        // 管理者の場合、user_name_kanaのバリデーションをスキップする
        if ("ADMIN".equals(roles)) {
            // user_name_kanaのバリデーションを無視
            result.rejectValue("user_name_kana", null);
        }

        // バリデーションエラーがあれば再度確認画面を表示
        if (result.hasErrors()) {
            log.debug("Validation errors found: {}", result.getAllErrors());
            model.addAttribute("userAddForm", form);
            return "useraddConfirm";
        }

        try {
            // ここで初めてデータベースに登録
            log.debug("Attempting to add user to the database");
            userAddService.addUser(form);
            log.debug("User successfully added, redirecting to user list");
            return "redirect:/userlist?addSuccess=true"; // 成功後にユーザー一覧へリダイレクト
        } catch (Exception e) {
            log.error("Error occurred during user registration", e);
            model.addAttribute("userAddError", "ユーザーの登録に失敗しました。再度お試しください。");
            model.addAttribute("userAddForm", form);
            return "useraddConfirm"; // 失敗時は確認画面を再表示
        }
    }
}
