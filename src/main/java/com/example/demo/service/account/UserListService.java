package com.example.demo.service.account;

import java.util.Optional;

import org.springframework.data.domain.Page;
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

    public Page<UserInfo> getAllUsers(Pageable pageable) {
        return userInfoRepository.findByDeletedFalse(pageable);
    }

    @Transactional
    public void toggleUserStatus(String loginId) {
        Optional<UserInfo> userOptional = userInfoRepository.findById(loginId);
        if (userOptional.isPresent()) {
            UserInfo user = userOptional.get();
            if ("ALLOWED".equals(user.getStatus())) {
                user.setStatus("DENIED");
            } else {
                user.setStatus("ALLOWED");
            }
            userInfoRepository.save(user);
        }
    }

    @Transactional
    public void softDeleteUser(String loginId) {
        Optional<UserInfo> userOptional = userInfoRepository.findById(loginId);
        if (userOptional.isPresent()) {
            UserInfo user = userOptional.get();
            user.setDeleted(true);  // deletedフラグをtrueに設定
            userInfoRepository.save(user);
        }
    }

    public UserInfo getUserByLoginId(String loginId) {
        return userInfoRepository.findById(loginId).orElse(null);
    }
}
