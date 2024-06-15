package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserListController {
    private final UserListService userListService;

    @GetMapping("/userlist")
    public String getUserList(@PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<UserInfo> userPage = userListService.getAllUsers(pageable);
        model.addAttribute("userPage", userPage);
        return "userlist";
    }

    @PostMapping("/userlist/toggle/{loginId}")
    @ResponseBody
    public String toggleUserStatus(@PathVariable String loginId) {
        userListService.toggleUserStatus(loginId);
        return "success";
    }

    @PostMapping("/userlist/delete/{loginId}")
    @ResponseBody
    public String deleteUser(@PathVariable String loginId) {
        userListService.deleteUser(loginId);
        return "success";
    }
}
