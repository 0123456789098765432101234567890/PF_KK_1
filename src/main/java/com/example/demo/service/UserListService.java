package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserListService {
    private final UserInfoRepository userInfoRepository;

    public Page<UserInfo> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userInfoRepository.findByDeletedFalse(pageable);
    }

    @Transactional
    public void toggleUserStatus(String loginId) {
        UserInfo user = userInfoRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if ("ALLOWED".equals(user.getStatus())) {
            user.setStatus("DENIED");
        } else {
            user.setStatus("ALLOWED");
        }
        userInfoRepository.save(user);
    }

    @Transactional
    public void deleteUser(String loginId) {
        UserInfo user = userInfoRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setDeleted(true);
        userInfoRepository.save(user);
    } 
}
