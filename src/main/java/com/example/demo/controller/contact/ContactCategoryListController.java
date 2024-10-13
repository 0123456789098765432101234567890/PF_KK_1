package com.example.demo.controller.contact;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.contact.ContactCategoryListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//お問い合わせカテゴリー一覧

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
    public String toggleCategory(@RequestParam("categoryId") Long categoryId, Model model) {
        contactCategoryListService.toggleCategoryDeleted(categoryId);
        model.addAttribute("message", "削除に成功しました。");
        return "redirect:/contactcategorylist";
    }
}
