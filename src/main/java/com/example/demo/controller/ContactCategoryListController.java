package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ContactCategoryListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactCategoryListController {
    private final ContactCategoryListService contactCategoryListService;

    @GetMapping("/contactcategorylist")
    public String getContactCategoryList(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("categoryPage", contactCategoryListService.getAllCategories(PageRequest.of(page, 5)));
        return "contactcategorylist";
    }

    @PostMapping("/contactcategorylist/toggle")
    @ResponseBody
    public String toggleCategoryDeleted(@RequestParam Long categoryId) {
        try {
            contactCategoryListService.toggleCategoryDeleted(categoryId);
            return "success";
        } catch (Exception e) {
            log.error("Error toggling deleted status for category_id: {}", categoryId, e);
            return "error";
        }
    }
}
