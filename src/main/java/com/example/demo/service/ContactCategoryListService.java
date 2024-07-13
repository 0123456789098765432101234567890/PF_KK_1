package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ContactCategory;
import com.example.demo.repository.ContactCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactCategoryListService {
    private final ContactCategoryRepository contactCategoryRepository;

    public Page<ContactCategory> getAllContactCategories(Pageable pageable) {
        return contactCategoryRepository.findAllByDeletedFalse(pageable);
    }

    public ContactCategory getContactCategoryById(Long categoryId) {
        return contactCategoryRepository.findById(categoryId).orElse(null);
    }

    public void toggleContactCategoryDeleted(Long categoryId) {
        ContactCategory contactCategory = getContactCategoryById(categoryId);
        if (contactCategory != null) {
            contactCategory.setDeleted(!contactCategory.isDeleted());
            contactCategoryRepository.save(contactCategory);
        }
    }
}
