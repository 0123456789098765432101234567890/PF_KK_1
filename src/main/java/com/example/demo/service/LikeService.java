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
        Optional<Like> like = likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            Like newLike = new Like();
            newLike.setLoginId(loginId);
            newLike.setFromLoginId(fromLoginId);
            newLike.setLikedAt(new Timestamp(System.currentTimeMillis()));
            likeRepository.save(newLike);
        }
    }

    @Transactional(readOnly = true)
    public boolean hasLiked(String loginId, String fromLoginId) {
        return likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId).isPresent();
    }
}
