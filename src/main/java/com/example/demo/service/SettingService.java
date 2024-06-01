package com.example.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo findUserByEmail(String email) {
        return userInfoRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void updateEmail(String loginId, String newEmail) {
        UserInfo userInfo = userInfoRepository.findById(loginId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userInfo.setEmail(newEmail);
        userInfoRepository.save(userInfo);
    }

    @Transactional
    public void updatePassword(String loginId, @Valid String newPassword) {
        UserInfo userInfo = userInfoRepository.findById(loginId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userInfo.setPass(passwordEncoder.encode(newPassword));
        userInfoRepository.save(userInfo);
    }
}
