package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contact_id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ContactCategory contactCategory;

    @Column(name = "contact_detail", nullable = false)
    private String contact_detail;

    @Column(name = "status", nullable = false)
    private String status;

    // コンストラクタ
    public Contact(ContactCategory contactCategory, String contactDetail) {
        this.contactCategory = contactCategory;
        this.contact_detail = contactDetail;
        this.status = "未対応"; // 初期値を設定
    }
}
