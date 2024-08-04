package com.example.demo.entity;

import java.util.Base64;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

    @Column(name = "self_intro", columnDefinition = "TEXT")
//  @Size(max = 1500, message = "Self introduction must be at most 1500 characters")
    private String selfIntro;
    
    @Column(name = "like_count")
    private long likeCount;

    @OneToMany(mappedBy = "loginId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;
    
    @Transient
    private boolean liked; // liked プロパティを追加
    
    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

	/** ユーザー権限 */
    @Column
    private String authority;

    public UserInfo() {}

    @Transient
    private String base64Image;

    public String getBase64Image() {
        if (this.profImg != null) {
            return Base64.getEncoder().encodeToString(this.profImg);
        } else {
            return "";
        }
    }
}
