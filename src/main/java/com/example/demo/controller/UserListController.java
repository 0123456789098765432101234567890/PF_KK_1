package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.form.UserListForm;
import com.example.demo.service.UserListService;

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
        model.addAttribute("userListForm", new UserListForm()); // ここでフォームオブジェクトを追加
        return "userlist";
    }

    @PostMapping("/userlist/toggle")
    @ResponseBody
    public String toggleUserStatus(@Valid UserListForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "error";
        }
        try {
            userListService.toggleUserStatus(form.getLoginId());
            return "success";
        } catch (Exception e) {
            log.error("Error toggling user status for loginId: {}", form.getLoginId(), e);
            return "error";
        }
    }

    @PostMapping("/userlist/delete")
    @ResponseBody
    public String deleteUser(@Valid UserListForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "error";
        }
        try {
            userListService.deleteUser(form.getLoginId());
            return "success";
        } catch (Exception e) {
            log.error("Error deleting user for loginId: {}", form.getLoginId(), e);
            return "error";
        }
    }

}
