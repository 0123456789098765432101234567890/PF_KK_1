package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
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

    // すべてのカテゴリーを取得
    public List<ContactCategory> getCategories() {
        return contactCategoryRepository.findByDeletedFalse(); // deletedがfalseのカテゴリーのみ取得
    }

    // ページングされたカテゴリーを取得
    public List<ContactCategory> getCategories(Pageable pageable) {
        return contactCategoryRepository.findAllByDeletedFalse(pageable).getContent();
    }

    // 新しいお問い合わせを保存
    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

    // IDによってカテゴリーを取得
    public ContactCategory getCategoryById(Long categoryId) {
        return contactCategoryRepository.findById(categoryId).orElse(null);
    }

    // すべての連絡先を取得
    public List<Contact> getAllContacts(Pageable pageable) {
        return contactRepository.findAllByOrderByContactIdDesc(pageable).getContent();
    }
}
