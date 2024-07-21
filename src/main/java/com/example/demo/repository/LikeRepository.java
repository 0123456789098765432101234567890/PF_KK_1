package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByLoginIdAndFromLoginId(String loginId, String fromLoginId);
    long countByLoginId(String loginId); // 追加
}
