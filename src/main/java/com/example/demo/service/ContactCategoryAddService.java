package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ContactCategory;
import com.example.demo.form.ContactCategoryForm;
import com.example.demo.repository.ContactCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactCategoryAddService {
    private final ContactCategoryRepository contactCategoryRepository;

    public void addCategory(ContactCategoryForm contactCategoryForm) {
        ContactCategory contactCategory = new ContactCategory();
        contactCategory.setCategory_name(contactCategoryForm.getCategory_name());
        contactCategory.setDeleted(false);
        contactCategoryRepository.save(contactCategory);
    }
}
