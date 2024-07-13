package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ContactCategory;
import com.example.demo.repository.ContactCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactCategoryEditService {
    private final ContactCategoryRepository contactCategoryRepository;

    public ContactCategory getCategoryById(Long categoryId) {
        return contactCategoryRepository.findById(categoryId).orElse(null);
    }

    public void updateCategory(ContactCategory contactCategory) {
        contactCategoryRepository.save(contactCategory);
    }
}
