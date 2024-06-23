package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        if (!model.containsAttribute("userAddForm")) {
            model.addAttribute("userAddForm", new UserAddForm());
        }
        return "useraddForm";
    }

    @PostMapping("/useradd")
    public String processUserAddForm(@Valid @ModelAttribute UserAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("Validation errors: {}", result.getAllErrors());
            model.addAttribute("userAddForm", form);
            return "useraddForm";
        }

        model.addAttribute("userAddForm", form);
        log.debug("Redirecting to confirmation page with form: {}", form);
        return "useraddConfirm";
    }

    @PostMapping("/useradd/register")
    public String registerUser(@Valid @ModelAttribute UserAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("Validation errors on confirmation: {}", result.getAllErrors());
            model.addAttribute("userAddForm", form);
            return "useraddConfirm";
        }
        try {
            userAddService.addUser(form);
            log.debug("User successfully added, redirecting to success page");
            return "redirect:/useradd/success";
        } catch (Exception e) {
            log.error("Error confirming user addition", e);
            model.addAttribute("userAddError", "Failed to confirm user addition. Please try again.");
            model.addAttribute("userAddForm", form);
            return "useraddConfirm";
        }
    }

    @GetMapping("/useradd/success")
    public String showSuccessPage() {
        return "useraddSuccess";
    }
}
