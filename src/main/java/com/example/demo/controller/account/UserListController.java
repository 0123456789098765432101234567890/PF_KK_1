package com.example.demo.controller.account;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.UserListForm;
import com.example.demo.service.account.UserListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserListController {

    private final UserListService userListService;

    @GetMapping("/userlist")
    public String getUserList(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("userPage", userListService.getAllUsers(PageRequest.of(page, 5)));
        model.addAttribute("userListForm", new UserListForm());
        return "userlist";
    }

    @PostMapping("/userlist/toggle")
    public String toggleUserStatus(@Valid @ModelAttribute UserListForm form, BindingResult result, Model model, @RequestParam(defaultValue = "0") int page) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "userlist";
        }
        try {
            userListService.toggleUserStatus(form.getLoginId());
            model.addAttribute("userPage", userListService.getAllUsers(PageRequest.of(page, 5)));
            return "redirect:/userlist"; // ステータス変更後にユーザー一覧画面にリダイレクト
        } catch (Exception e) {
            log.error("Error toggling user status for loginId: {}", form.getLoginId(), e);
            return "error";
        }
    }

    @PostMapping("/userlist/delete")
    public String deleteUser(@Valid @ModelAttribute UserListForm form, BindingResult result, Model model, @RequestParam(defaultValue = "0") int page) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "userlist";
        }
        try {
            userListService.softDeleteUser(form.getLoginId());
            model.addAttribute("userPage", userListService.getAllUsers(PageRequest.of(page, 5)));
            return "redirect:/userlist"; // 削除後にユーザー一覧画面にリダイレクト
        } catch (Exception e) {
            log.error("Error deleting user for loginId: {}", form.getLoginId(), e);
            return "error";
        }
    }
}
