package com.example.demo.controller;

import java.util.Base64;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserListForm;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.UserListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserListController {
    private final UserListService userListService;
    private final UserInfoService userInfoService;

    @GetMapping("/userlist")
    public String getUserList(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("userPage", userListService.getAllUsers(PageRequest.of(page, 5)));
        model.addAttribute("userListForm", new UserListForm());
        return "userlist";
    }

    @PostMapping("/userlist/toggle")
    @ResponseBody
    public String toggleUserStatus(@Valid @ModelAttribute UserListForm form, BindingResult result) {
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
    public String deleteUser(@Valid @ModelAttribute UserListForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "error";
        }
        try {
            userListService.deleteUser(form.getLoginId());
            return "deleteResult"; // deleteResult.htmlに対応するビュー名を返す
        } catch (Exception e) {
            log.error("Error deleting user for loginId: {}", form.getLoginId(), e);
            return "error";
        }
    }

    @GetMapping("/userlist/{loginId}")
    public String viewUser(@PathVariable String loginId, Model model) {
        UserInfo user = userInfoService.findByLoginId(loginId);
        if (user == null) {
            log.error("User with loginId: {} not found", loginId);
            return "error"; // エラービューにリダイレクト
        }
        model.addAttribute("user", user);
        if (user.getProfImg() != null) {
            String profImgBase64 = Base64.getEncoder().encodeToString(user.getProfImg());
            model.addAttribute("profImgBase64", profImgBase64);
        }
        return "userdetail";
    }
}
