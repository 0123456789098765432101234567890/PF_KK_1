package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.ContactCategory;
import com.example.demo.service.ContactCategoryEditService;
import com.example.demo.service.ContactCategoryListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactCategoryEditController {
    private final ContactCategoryListService contactCategoryListService;
    private final ContactCategoryEditService contactCategoryEditService;

    @GetMapping("/contactcategorylist/{categoryId}")
    public String getContactCategoryDetail(@PathVariable("categoryId") Long categoryId, Model model) {
        ContactCategory contactCategory = contactCategoryListService.getContactCategoryById(categoryId);
        if (contactCategory == null) {
            log.error("Contact category with id: {} not found", categoryId);
            return "error"; // エラービューにリダイレクト
        }
        model.addAttribute("contactCategory", contactCategory);
        return "contactcategoryedit";
    }

    @PostMapping("/contactcategorylist/update")
    @ResponseBody
    public String updateContactCategory(@RequestParam Long categoryId, @RequestParam String categoryName) {
        try {
            contactCategoryEditService.updateContactCategory(categoryId, categoryName);
            return "success";
        } catch (Exception e) {
            log.error("Error updating contact category for category_id: {}", categoryId, e);
            return "error";
        }
    }
}
