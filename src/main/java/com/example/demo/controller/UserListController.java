package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserListController {
    private final UserListService userListService;

    @GetMapping("/userlist")
    public String listUsers(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<UserInfo> userPage = userListService.getUsers(page, 5); // 5件ごとにページング
        model.addAttribute("userPage", userPage);
        return "userList";
    }

    @PostMapping("/userlist/toggle/{loginId}")
    public String toggleUserStatus(@PathVariable String loginId) {
        userListService.toggleUserStatus(loginId);
        return "redirect:/userlist";
    }

    @PostMapping("/userlist/delete/{loginId}")
    public String deleteUser(@PathVariable String loginId) {
        userListService.deleteUser(loginId);
        return "redirect:/userlist";
    }
}
