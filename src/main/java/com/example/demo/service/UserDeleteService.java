package com.example.demo.service;

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
public class UserDeleteService {
    private final UserInfoRepository userInfoRepository;

    public Page<UserInfo> getAllDeletedUsers(Pageable pageable) {
        return userInfoRepository.findByDeleted(true, pageable);
    }

    @Transactional
    public void restoreUser(String loginId) {
        Optional<UserInfo> userOptional = userInfoRepository.findById(loginId);
        if (userOptional.isPresent()) {
            UserInfo user = userOptional.get();
            user.setDeleted(false);
            userInfoRepository.save(user);
        }
    }

    @Transactional
    public void deleteUserPermanently(String loginId) {
        userInfoRepository.deleteById(loginId);
    }
}
