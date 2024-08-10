package com.example.demo.form;

import com.example.demo.entity.ContactCategory;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ContactForm {

    private Long category_id; // カテゴリーIDを保存

    @NotEmpty(message = "お問い合わせ内容を入力してください。") // お問い合わせ内容が必要
    private String contact_detail; // お問い合わせの詳細

    private ContactCategory contactCategory; // カテゴリーエンティティを保存

    public String getCategoryName() {
        // ContactCategory エンティティがフォームにロードされていることを想定
        return this.contactCategory != null ? this.contactCategory.getCategory_name() : "";
    }
}
