package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.ContactCategoryForm;
import com.example.demo.service.ContactCategoryAddService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactCategoryAddController {
    private final ContactCategoryAddService contactCategoryAddService;

    @GetMapping("/contactcategoryadd")
    public String showAddForm(Model model) {
        model.addAttribute("contactCategoryForm", new ContactCategoryForm());
        return "contactcategoryadd";
    }

    @PostMapping("/contactcategoryadd")
    public String addCategory(@Valid @ModelAttribute ContactCategoryForm contactCategoryForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("contactCategoryForm", contactCategoryForm);
            return "contactcategoryadd";
        }
        contactCategoryAddService.addCategory(contactCategoryForm);
        model.addAttribute("message", "カテゴリーを追加しました。");
        return "redirect:/contactcategorylist";
    }
}
