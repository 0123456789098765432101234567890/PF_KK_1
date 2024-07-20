package com.example.demo.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Like;
import com.example.demo.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public void likeUser(String loginId, String fromLoginId) {
        Optional<Like> existingLike = likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId);
        if (existingLike.isEmpty()) {
            Like like = new Like();
            like.setLoginId(loginId);
            like.setFromLoginId(fromLoginId);
            like.setLikedAt(new Timestamp(System.currentTimeMillis()));
            likeRepository.save(like);
        }
    }

    @Transactional
    public void unlikeUser(String loginId, String fromLoginId) {
        Optional<Like> existingLike = likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId);
        existingLike.ifPresent(likeRepository::delete);
    }

    public boolean hasLiked(String loginId, String fromLoginId) {
        return likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId).isPresent();
    }

    public Long getLikeCount(String loginId) {
        return likeRepository.countByLoginId(loginId);
    }
}
