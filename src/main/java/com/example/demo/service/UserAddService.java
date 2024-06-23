package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserAddForm;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddService {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addUser(UserAddForm form) {
        UserInfo user = new UserInfo();
        user.setLoginId(form.getEmail()); // Assuming loginId is the email
        user.setUser_name(form.getUser_name());
        user.setEmail(form.getEmail());
        user.setPass(passwordEncoder.encode(form.getPass()));
        user.setStatus(form.getStatus());
        user.setRoles(form.getRoles());

        // Handle profile image if present
        if (form.getProf_img() != null && !form.getProf_img().isEmpty()) {
            try {
                user.setProfImg(form.getProf_img().getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Failed to store profile image", e);
            }
        }

        // Additional fields for "USER" role
        if (form.getRoles().equals("USER")) {
            user.setUserNameKana(form.getUser_name_kana());
            user.setGender(form.getGender());
            user.setAge(form.getAge());
            user.setSelfIntro(form.getSelf_intro());
        }

        userInfoRepository.save(user);
    }
}
