package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ユーザ情報テーブル Entity
 * 
 * @author ys-fj
 *
 */
@Entity
@Table(name = "user_info")
@Data
@AllArgsConstructor
public class UserInfo {

	/** ログインID */
	@Id
    @Column(name = "login_id")    
    private String loginId;
    
    @Column(name = "pass")
    private String pass;
    
    private String email;
    
    @Column(name = "user_name")
    private String user_name;
    
    @Column(name = "status")
    private String status; // "ALLOWED" or "DENIED"
    
    @Column(name = "deleted")
    private boolean deleted; // logical delete flag
	
    @Column(name = "roles")
    private String roles; // "ADMIN" or "USER"
    
    @Column(name = "prof_img", columnDefinition = "MEDIUMBLOB")
    private byte[] profImg;

    @Column(name = "user_name_kana")
    private String userNameKana;

    @Column(name = "gender")
    private String gender; // "男性", "女性", "その他"

    @Column(name = "age")
    private Integer age;

    @Column(name = "self_intro")
    @Size(max = 1500, message = "Self introduction must be at most 1500 characters")
    private String selfIntro;

	/** ユーザー権限 */
	@Column
	private String authority;

	public UserInfo() {
	}

}
