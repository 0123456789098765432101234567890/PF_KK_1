package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.ContactCategory;
import com.example.demo.service.ContactCategoryEditService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactCategoryEditController {
    private final ContactCategoryEditService contactCategoryEditService;

    @GetMapping("/contactcategoryedit/{categoryId}")
    public String showEditForm(@PathVariable Long categoryId, Model model) {
        ContactCategory contactCategory = contactCategoryEditService.getCategoryById(categoryId);
        if (contactCategory == null) {
            log.error("ContactCategory with id: {} not found", categoryId);
            return "error"; // エラービューにリダイレクト
        }
        model.addAttribute("contactCategory", contactCategory);
        return "contactcategoryedit";
    }

    @PostMapping("/contactcategoryedit/update")
    public String updateCategory(@Valid @ModelAttribute ContactCategory contactCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("contactCategory", contactCategory);
            return "contactcategoryedit";
        }
        contactCategoryEditService.updateCategory(contactCategory);
        return "redirect:/contactcategorylist";
    }
}
