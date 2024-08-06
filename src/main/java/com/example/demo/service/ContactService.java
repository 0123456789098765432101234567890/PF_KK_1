package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactCategory;
import com.example.demo.repository.ContactCategoryRepository;
import com.example.demo.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactCategoryRepository contactCategoryRepository;

    public List<ContactCategory> getCategories() {
        return contactCategoryRepository.findAllByDeletedFalse();
    }

    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

    public ContactCategory getCategoryById(Long categoryId) {
        return contactCategoryRepository.findById(categoryId).orElse(null);
    }
}
