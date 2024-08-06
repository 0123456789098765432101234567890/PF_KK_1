package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ContactCategory;

public interface ContactCategoryRepository extends JpaRepository<ContactCategory, Long> {
    Page<ContactCategory> findAllByDeletedFalse(Pageable pageable);
    List<ContactCategory> findByDeletedFalse(); // deletedがfalseのカテゴリーのみ取得
}
