package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserSettingForm;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSettingService {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateUserSettings(String loginId, UserSettingForm form) {
        UserInfo user = userInfoRepository.findById(loginId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        user.setEmail(form.getEmail());
        user.setPass(passwordEncoder.encode(form.getPass()));
        userInfoRepository.save(user);
    }
}
