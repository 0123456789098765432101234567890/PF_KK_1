package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserEditForm;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEditService {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateUser(UserEditForm form) {
        UserInfo user = userInfoRepository.findById(form.getLoginId()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setUser_name(form.getUser_name());
        user.setEmail(form.getEmail());
        user.setPass(passwordEncoder.encode(form.getPass()));
        user.setStatus(form.getStatus());
        user.setRoles(form.getRoles());

        if (form.getProfImgBytes() != null) {
            user.setProfImg(form.getProfImgBytes());
        }

        if ("USER".equals(form.getRoles())) {
            user.setUserNameKana(form.getUser_name_kana());
            user.setGender(form.getGender());
            user.setAge(form.getAge());
            user.setSelfIntro(form.getSelf_intro());
        }

        log.debug("Updating user: {}", user);
        userInfoRepository.save(user);
    }
}
