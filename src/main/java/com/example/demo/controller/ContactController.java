package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactCategory;
import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        List<ContactCategory> categories = contactService.getCategories();
        model.addAttribute("categories", categories);

        if (!model.containsAttribute("contactForm")) {
            model.addAttribute("contactForm", new ContactForm());
        }

        return "contactForm";
    }

    @PostMapping("/contact")
    public String processContactForm(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("Validation errors: {}", result.getAllErrors());
            return "contactForm";
        }

        log.debug("Redirecting to confirmation page with form: {}", contactForm.getContact_detail());
        List<ContactCategory> categories = contactService.getCategories();
        model.addAttribute("categories", categories); // 確認画面にもカテゴリ情報を渡す

        // カテゴリ名を取得
        ContactCategory category = contactService.getCategoryById(contactForm.getCategory_id());
        String categoryName = category != null ? category.getCategory_name() : "Unknown Category";

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("contactForm", contactForm);

        return "contactConfirm";
    }

    @PostMapping("/contact/register")
    public String registerContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("Validation errors on confirmation: {}", result.getAllErrors());
            return "contactConfirm";
        }

        try {
            Contact contact = new Contact();
            contact.setContactCategory(contactService.getCategoryById(contactForm.getCategory_id()));
            contact.setContact_detail(contactForm.getContact_detail());
            contact.setStatus("未対応");

            contactService.saveContact(contact);
            log.debug("Contact successfully added, redirecting to success page");

            return "redirect:/contact/success";
        } catch (Exception e) {
            log.error("Error confirming contact addition", e);
            model.addAttribute("contactAddError", "Failed to confirm contact addition. Please try again.");
            return "contactConfirm";
        }
    }

    @GetMapping("/contact/success")
    public String showSuccessPage() {
        return "contactSuccess";
    }
}
