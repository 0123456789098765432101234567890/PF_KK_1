package com.example.demo.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Like;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserInfoRepository userInfoRepository;

    public void likeUser(String loginId, String fromLoginId) {
        log.debug("Attempting to like user with loginId: {}", loginId);
        UserInfo user = userInfoRepository.findById(loginId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + loginId));
        Like like = new Like();
        like.setLoginId(loginId);
        like.setFromLoginId(fromLoginId);
        like.setLikedAt(Timestamp.from(Instant.now()));
        likeRepository.save(like);
        log.debug("User with loginId: {} liked successfully by: {}", loginId, fromLoginId);
    }

    public void unlikeUser(String loginId, String fromLoginId) {
        log.debug("Attempting to unlike user with loginId: {}", loginId);
        Like like = likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId)
            .orElseThrow(() -> new IllegalArgumentException("No like found for user ID: " + loginId));
        likeRepository.delete(like);
        log.debug("User with loginId: {} unliked successfully by: {}", loginId, fromLoginId);
    }

    public boolean hasLiked(String loginId, String fromLoginId) {
        return likeRepository.findByLoginIdAndFromLoginId(loginId, fromLoginId).isPresent();
    }
}
