package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // 特定のユーザーが他のユーザーに対していいねをした履歴を取得
    Optional<Like> findByLoginIdAndFromLoginId(String loginId, String fromLoginId);

    // 特定のユーザーが受けたいいねの総数を取得
    long countByLoginId(String loginId);

    // 指定された期間内で特定のユーザーが受けたいいねの数をカウント
    long countByLoginIdAndLikedAtBetween(String loginId, LocalDateTime start, LocalDateTime end);

    // 指定された期間内で特定のユーザーが受けたいいねをリストとして取得
    List<Like> findAllByLoginIdAndLikedAtBetween(String loginId, LocalDateTime start, LocalDateTime end);
}
