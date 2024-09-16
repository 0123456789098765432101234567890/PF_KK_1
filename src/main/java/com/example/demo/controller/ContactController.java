package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;
import com.example.demo.service.MailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;
    private final MailService mailService;

    // お問い合わせフォームを表示
    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        model.addAttribute("categories", contactService.getCategories());
        return "contactForm";
    }

    // 入力内容確認画面への遷移
    @PostMapping("/contact")
    public String confirmContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", contactService.getCategories());
            return "contactForm";
        }

        // カテゴリー名を表示用に設定
        String categoryName = contactService.getCategoryById(contactForm.getCategory_id()).getCategory_name();
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("contactForm", contactForm);

        return "contactConfirm";
    }

    // 確認画面から送信
    @PostMapping("/contact/confirm")
    public String sendContact(@ModelAttribute ContactForm contactForm) {
        // メール通知を送信
        String to = "k.kondo@oplan.co.jp"; // 通知先のメールアドレス
        String subject = "お問い合わせ通知";
        String message = buildEmailMessage(contactForm);

        mailService.sendMail(to, subject, message);

        // ContactFormをContactエンティティに変換
        Contact contact = convertToContactEntity(contactForm);

        // データベースに保存
        contactService.saveContact(contact);

        // ポップアップメッセージ表示後、/contactlistにリダイレクト
        return "redirect:/contactlistUser";
    }

    // ContactFormをContactエンティティに変換するメソッド
    private Contact convertToContactEntity(ContactForm contactForm) {
        Contact contact = new Contact();
        contact.setContactCategory(contactService.getCategoryById(contactForm.getCategory_id()));
        contact.setContact_detail(contactForm.getContact_detail());
        contact.setStatus("未対応"); // 新しいお問い合わせは常に「未対応」で開始
        return contact;
    }

    // メールの内容を構築
    private String buildEmailMessage(ContactForm contactForm) {
        StringBuilder sb = new StringBuilder();
        sb.append("お問い合わせが送信されました。\n\n");
        sb.append("送信日時: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("カテゴリー: ").append(contactService.getCategoryById(contactForm.getCategory_id()).getCategory_name()).append("\n");
        sb.append("お問い合わせ内容:\n").append(contactForm.getContact_detail()).append("\n");
        return sb.toString();
    }
}
