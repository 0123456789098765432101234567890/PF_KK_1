package com.example.demo.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Like;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserInfoRepository userInfoRepository;

    public void likeUser(String loginId, String fromLoginId) {
        UserInfo user = userInfoRepository.findById(loginId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Like like = new Like();
        like.setLoginId(loginId);
        like.setFromLoginId(fromLoginId);
        like.setLikedAt(Timestamp.from(Instant.now()));
        likeRepository.save(like);
    }

    public void unlikeUser(String loginId, String fromLoginId) {
        Like like = likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId)
                                  .orElseThrow(() -> new IllegalArgumentException("No like found for user ID"));
        likeRepository.delete(like);
    }

    public boolean hasLiked(String loginId, String fromLoginId) {
        return likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId).isPresent();
    }
}
