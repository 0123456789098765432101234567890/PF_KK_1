package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;

/**
 * ユーザー情報テーブルDAO
 * 
 * @author ys-fj
 *
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByEmail(String email);

    // 論理削除されていないユーザーの一覧をページングして取得
    Page<UserInfo> findByDeletedFalse(Pageable pageable);

    // 特定のユーザーのステータスを更新するメソッドはデフォルトメソッドをサービス層で実装可能
}
