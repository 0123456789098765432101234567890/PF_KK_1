package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ContactForm {

    private Long category_id; // カテゴリーIDを保存

    @NotEmpty(message = "お問い合わせ内容を入力してください。") // お問い合わせ内容が必要
    private String contact_detail; // お問い合わせの詳細
}
