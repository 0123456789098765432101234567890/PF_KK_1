package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactCategory {
    
    @Id
    @Column(name = "category_id")
    private Long category_id;
    
    @Column(name = "category_name")
    private String category_name;
}
