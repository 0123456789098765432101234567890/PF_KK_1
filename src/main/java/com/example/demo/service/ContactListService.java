package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactListService {
    private final ContactRepository contactRepository;

    public Page<Contact> getAllContacts(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }

    public Contact getContactById(Long contactId) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        return contact.orElse(null);
    }

    @Transactional
    public void updateContactStatus(Long contactId, String status) {
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            contact.setStatus(status);
            contactRepository.save(contact);
        }
    }
}
