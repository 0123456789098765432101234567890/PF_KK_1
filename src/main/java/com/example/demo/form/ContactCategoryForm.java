package com.example.demo.form;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactCategoryForm {

    // category_id フィールドを追加
    private Long category_id;

    @Size(max = 255, message = "カテゴリー名は255文字以内で入力してください。")
    private String category_name;
}
