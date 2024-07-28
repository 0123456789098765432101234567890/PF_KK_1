package com.example.demo.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public List<UserInfo> getAllUsersWithRoleUser() {
        String currentUserName = getCurrentUserName();
        return userInfoRepository.findByRoles("USER").stream()
                .filter(user -> !user.getLoginId().equals(currentUserName))
                .map(user -> {
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    user.setLikeCount(likeService.countLikesByLoginId(user.getLoginId()));
                    return user;
                })
                .collect(Collectors.toList());
    }

    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll().stream()
                .map(user -> {
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    user.setLikeCount(likeService.countLikesByLoginId(user.getLoginId()));
                    return user;
                })
                .collect(Collectors.toList());
    }

    private String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
