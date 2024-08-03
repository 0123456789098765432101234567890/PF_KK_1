package com.example.demo.service.contact;

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

    public Page<ContactCategory> getAllCategories(Pageable pageable) {
        return contactCategoryRepository.findAllByDeletedFalse(pageable);
    }

    public void toggleCategoryDeleted(Long categoryId) {
        ContactCategory category = contactCategoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
        category.setDeleted(!category.isDeleted());
        contactCategoryRepository.save(category);
    }
}
