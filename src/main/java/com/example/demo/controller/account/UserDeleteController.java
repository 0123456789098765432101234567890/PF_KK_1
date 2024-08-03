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
import com.example.demo.service.account.UserDeleteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserDeleteController {
    private final UserDeleteService userDeleteService;

    @GetMapping("/userdelete")
    public String getUserDeleteList(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("userPage", userDeleteService.getAllDeletedUsers(PageRequest.of(page, 5)));
        model.addAttribute("userListForm", new UserListForm());
        return "userdelete";
    }

    @PostMapping("/userdelete/restore")
    public String restoreUser(@Valid @ModelAttribute UserListForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "error";
        }
        try {
            userDeleteService.restoreUser(form.getLoginId());
            return "redirect:/userdelete";
        } catch (Exception e) {
            log.error("Error restoring user for loginId: {}", form.getLoginId(), e);
            return "error";
        }
    }

    @PostMapping("/userdelete/permanentDelete")
    public String permanentDeleteUser(@Valid @ModelAttribute UserListForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "error";
        }
        try {
            userDeleteService.deleteUserPermanently(form.getLoginId());
            return "redirect:/userdelete";
        } catch (Exception e) {
            log.error("Error permanently deleting user for loginId: {}", form.getLoginId(), e);
            return "error";
        }
    }
}
