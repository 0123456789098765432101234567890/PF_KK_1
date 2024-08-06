package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ContactForm {

    private Long category_id;

    @NotEmpty(message = "お問い合わせ内容を入力してください。")
    private String contact_detail;
}
