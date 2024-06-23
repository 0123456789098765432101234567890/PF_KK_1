package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UserAddForm;
import com.example.demo.service.UserAddService;

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
        model.addAttribute("userAddForm", new UserAddForm());
        return "useraddForm";
    }

    @PostMapping("/useradd")
    public String addUser(@Valid UserAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "useraddForm";
        }

        try {
            userAddService.addUser(form);
            model.addAttribute("userAddForm", form);
            return "useraddConfirm";
        } catch (Exception e) {
            log.error("Error adding user", e);
            model.addAttribute("userAddError", "Failed to add user. Please try again.");
            return "useraddForm";
        }
    }

    @PostMapping("/useradd/confirm")
    public String confirmUserAdd(@Valid UserAddForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "useraddConfirm";
        }
        userAddService.addUser(form);
        return "redirect:/useradd/success";
    }
}
