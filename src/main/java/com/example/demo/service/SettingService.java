package com.example.demo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo getCurrentUserInfo() {
        String currentLoginId = getCurrentLoginId();
        return userInfoRepository.findById(currentLoginId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateUserInfo(UserInfo userInfo) {
        if (userInfo.getPass() != null && !userInfo.getPass().isEmpty()) {
            userInfo.setPass(passwordEncoder.encode(userInfo.getPass()));
        }
        userInfoRepository.save(userInfo);
    }

    private String getCurrentLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
