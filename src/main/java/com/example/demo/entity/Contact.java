package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "contact_id")
    private Long contact_id;
    
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ContactCategory contactCategory;
    
    private String contact_detail;
    private String status;
}
