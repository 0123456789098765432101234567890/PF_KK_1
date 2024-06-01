package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @NotEmpty(message = "ろぐいんあいでぃーはひっすです" /*"Login ID is required" */)
    private String loginId;
    
    @Column(name = "pass")
    @NotEmpty(message = "ぱすわあどはひっすです" /* "Password is required" */)
    @Size(min = 3, max = 32, message = "Password must be between 3 and 32 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Password can only contain alphanumeric characters, dashes, and underscores")
    private String pass;
    
    @Column(name = "email")
    @NotEmpty(message = "めいるあどれすはひっすです" /* "Email is required" */)
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email should be at most 255 characters")
    private String email;
	

	/** ログイン失敗回数 */
/*	@Column(name = "login_failure_count")
	private int loginFailureCount = 0; */

	/** アカウントロック日時 */
/*	@Column(name = "account_locked_time")
	private LocalDateTime accountLockedTime; */

	/** 利用可能か(true:利用可能) */
/*	@Column(name = "is_disabled")
	private boolean isDisabled; */

	/** ユーザー権限 */
	@Column
	private String authority;

	public UserInfo() {
	}

	/**
	 * ログイン失敗回数をインクリメントする
	 * 
	 * @return ログイン失敗回数がインクリメントされたUserInfo
	 */
/*	public UserInfo incrementLoginFailureCount() {
		return new UserInfo(loginId, password, ++loginFailureCount, accountLockedTime, isDisabled, authority);
	} */

	/**
	 * ログイン失敗情報をリセットする
	 * 
	 * @return ログイン失敗情報がリセットされたUserInfo
	 */
/*	public UserInfo resetLoginFailureInfo() {
		return new UserInfo(loginId, password, 0, null, isDisabled, authority);
	} */

	/**
	 * アカウントロック状態に更新する
	 * 
	 * @return ログイン失敗階位数、アカウントロック日時が更新されたUserInfo
	 */
/*	public UserInfo updateAccountLocked() {
		return new UserInfo(loginId, password, 0, LocalDateTime.now(), isDisabled, authority);
	} */

}
