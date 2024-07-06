package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.ContactListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactListController {
    private final ContactListService contactListService;

    @GetMapping("/contactlist")
    public String getContactList(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("contactPage", contactListService.getAllContacts(PageRequest.of(page, 5)));
        return "contactlist";
    }
}
