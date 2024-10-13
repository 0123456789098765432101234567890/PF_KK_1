package com.example.demo.controller.contact;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.ContactCategory;
import com.example.demo.form.ContactCategoryForm;
import com.example.demo.service.contact.ContactCategoryEditService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//お問い合わせカテゴリー編集

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactCategoryEditController {

    private final ContactCategoryEditService contactCategoryEditService;

    @GetMapping("/contactcategoryedit/{categoryId}")
    public String showEditForm(@PathVariable Long categoryId, Model model) {
        ContactCategory contactCategory = contactCategoryEditService.getCategoryById(categoryId);
        if (contactCategory == null) {
            log.error("ContactCategory with id: {} not found", categoryId);
            return "error"; // エラービューにリダイレクト
        }

        // ContactCategoryエンティティをフォームクラスに変換してモデルに追加
        ContactCategoryForm form = new ContactCategoryForm();
        form.setCategory_id(contactCategory.getCategory_id()); // category_id をしっかりセット
        form.setCategory_name(contactCategory.getCategory_name()); // カテゴリー名をセット
        model.addAttribute("contactCategoryForm", form);
        model.addAttribute("contactCategory", contactCategory); // エンティティも保持
        return "contactcategoryedit";
    }

    @PostMapping("/contactcategoryedit/update")
    public String updateCategory(@Valid @ModelAttribute("contactCategoryForm") ContactCategoryForm contactCategoryForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("contactCategoryForm", contactCategoryForm);
            model.addAttribute("contactCategory", contactCategoryEditService.getCategoryById(contactCategoryForm.getCategory_id())); // エラー時にもエンティティを保持
            return "contactcategoryedit"; // バリデーションエラーがあれば再表示
        }

        // IDに基づいてエンティティを取得し、フォームのデータで更新
        ContactCategory contactCategory = contactCategoryEditService.getCategoryById(contactCategoryForm.getCategory_id());
        if (contactCategory == null) {
            log.error("ContactCategory with id: {} not found");
            return "error"; // エンティティが見つからない場合
        }

        contactCategory.setCategory_name(contactCategoryForm.getCategory_name());
        contactCategoryEditService.updateCategory(contactCategory); // 更新処理

        return "redirect:/contactcategorylist"; // 更新後、カテゴリー一覧へリダイレクト
    }
}
