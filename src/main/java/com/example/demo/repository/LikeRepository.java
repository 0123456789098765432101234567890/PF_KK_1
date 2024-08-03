package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByLoginIdAndFromLoginId(String loginId, String fromLoginId);

    long countByLoginId(String loginId);

    // 追加: 指定された期間内のいいね数をカウント
    long countByLoginIdAndLikedAtBetween(String loginId, LocalDateTime start, LocalDateTime end);

    // ユーザーの月間いいね数を取得するメソッド
    List<Like> findAllByLoginIdAndLikedAtBetween(String loginId, LocalDateTime start, LocalDateTime end);
}
