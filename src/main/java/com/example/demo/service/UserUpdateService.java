package com.example.demo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserUpdateForm;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUpdateService {
    private final UserInfoRepository userInfoRepository;

    public UserInfo getCurrentUser() {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userInfoRepository.findById(loginId).orElse(null);
    }

    @Transactional
    public void updateUser(UserUpdateForm form) {
        UserInfo user = userInfoRepository.findById(form.getLoginId()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setUser_name(form.getUserName());
        user.setUserNameKana(form.getUserNameKana());
        user.setGender(form.getGender());
        user.setAge(form.getAge());
        user.setSelfIntro(form.getSelfIntro());

        if (form.getProfImgBytes() != null) {
            user.setProfImg(form.getProfImgBytes());
        }

        log.debug("Updating user with loginId: {}, username: {}", user.getLoginId(), user.getUser_name());
        userInfoRepository.save(user);
    }
}
