package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateUser(UserUpdateForm form) {
        UserInfo user = userInfoRepository.findById(form.getLoginId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + form.getLoginId()));

        user.setUser_name(form.getUserName());
        user.setUserNameKana(form.getUserNameKana());
        user.setGender(form.getGender());
        user.setAge(form.getAge());
        user.setSelfIntro(form.getSelfIntro());

        // プロファイル画像をバイト配列から設定
        if (form.getProfImgBytes() != null) {
            user.setProfImg(form.getProfImgBytes());
            log.debug("Profile image size: {} bytes", form.getProfImgBytes().length);
        } else {
            log.debug("No profile image to store");
        }

        log.debug("Saving user with username: {}, email: {}", user.getUser_name(), user.getEmail()); // 必要な情報のみ出力
        userInfoRepository.save(user);
    }

    public UserUpdateForm getUserUpdateForm(String loginId) {
        UserInfo user = userInfoRepository.findById(loginId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + loginId));
        UserUpdateForm form = new UserUpdateForm();
        form.setLoginId(user.getLoginId());
        form.setUserName(user.getUser_name());
        form.setUserNameKana(user.getUserNameKana());
        form.setGender(user.getGender());
        form.setAge(user.getAge());
        form.setSelfIntro(user.getSelfIntro());
        return form;
    }
}
