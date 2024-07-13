package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactListService {
    private final ContactRepository contactRepository;

    public Page<Contact> getAllContacts(Pageable pageable) {
        return contactRepository.findAllByOrderByContactIdDesc(pageable);
    }

    public Contact getContactById(Long contactId) {
        return contactRepository.findById(contactId).orElse(null);
    }

    public void updateContactStatus(Long contactId, String status) {
        Contact contact = getContactById(contactId);
        if (contact != null) {
            contact.setStatus(status);
            contactRepository.save(contact);
        }
    }
}
