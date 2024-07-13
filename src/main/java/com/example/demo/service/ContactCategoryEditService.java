package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.ContactCategory;
import com.example.demo.repository.ContactCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactCategoryEditService {
    private final ContactCategoryRepository contactCategoryRepository;

    @Transactional
    public void updateContactCategory(Long categoryId, String categoryName) {
        ContactCategory contactCategory = contactCategoryRepository.findById(categoryId).orElse(null);
        if (contactCategory != null) {
            contactCategory.setCategory_name(categoryName);
            contactCategoryRepository.save(contactCategory);
        }
    }
}
