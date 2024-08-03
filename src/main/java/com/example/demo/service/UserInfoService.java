package com.example.demo.service;

import java.util.Base64;
import java.util.Collections; // 追加
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final LikeService likeService;

    public Page<UserInfo> findAll(Pageable pageable) {
        return userInfoRepository.findAll(pageable);
    }

    public UserInfo findByLoginId(String loginId) {
        return userInfoRepository.findById(loginId).orElse(null);
    }

    public List<UserInfo> getAllUsersWithRoleUser(String currentLoginId) {
        List<UserInfo> users = userInfoRepository.findByRoles("USER").stream()
                .filter(user -> !user.getLoginId().equals(currentLoginId)) // ログイン中のユーザーを除外
                .map(user -> {
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    user.setLikeCount(likeService.countLikesByLoginId(user.getLoginId()));
                    return user;
                })
                .collect(Collectors.toList());

        Collections.shuffle(users); // ユーザーリストをランダムに並び替え
        return users;
    }

    public List<UserInfo> getAllUsers() {
        List<UserInfo> users = userInfoRepository.findAll().stream()
                .map(user -> {
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    user.setLikeCount(likeService.countLikesByLoginId(user.getLoginId()));
                    return user;
                })
                .collect(Collectors.toList());

        Collections.shuffle(users); // ユーザーリストをランダムに並び替え
        return users;
    }
}
