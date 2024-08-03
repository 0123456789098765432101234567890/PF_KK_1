package com.example.demo.controller.contact;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Contact;
import com.example.demo.service.contact.ContactListService;

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

    @GetMapping("/contactlist/{contact_id}")
    public String getContactDetail(@PathVariable("contact_id") Long contactId, Model model) {
        Contact contact = contactListService.getContactById(contactId);
        if (contact == null) {
            log.error("Contact with id: {} not found", contactId);
            return "error"; // エラービューにリダイレクト
        }
        model.addAttribute("contact", contact);
        return "contactdetail";
    }

    @PostMapping("/contactlist/update")
    @ResponseBody
    public String updateContactStatus(@ModelAttribute Contact contact) {
        try {
            contactListService.updateContactStatus(contact.getContact_id(), contact.getStatus());
            return "success";
        } catch (Exception e) {
            log.error("Error updating contact status for contact_id: {}", contact.getContact_id(), e);
            return "error";
        }
    }
}
