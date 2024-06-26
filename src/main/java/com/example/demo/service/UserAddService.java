package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserAddForm;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAddService {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addUser(UserAddForm form) {
        UserInfo user = new UserInfo();
        user.setLoginId(form.getUser_name()); // login_idにuser_nameをセットする
        user.setUser_name(form.getUser_name());
        user.setEmail(form.getEmail());
        user.setPass(passwordEncoder.encode(form.getPass()));
        user.setStatus(form.getStatus());
        user.setRoles(form.getRoles());

        // プロファイル画像をバイト配列から設定
        if (form.getProfImgBytes() != null) {
            user.setProfImg(form.getProfImgBytes());
            log.debug("Profile image size: {} bytes", form.getProfImgBytes().length);
        } else {
            log.debug("No profile image to store");
        }

        // "USER" ロールの場合の追加フィールド
        if (form.getRoles().equals("USER")) {
            user.setUserNameKana(form.getUser_name_kana());
            user.setGender(form.getGender());
            user.setAge(form.getAge());
            user.setSelfIntro(form.getSelf_intro());
        }

        log.debug("Saving user: {}", user);
        userInfoRepository.save(user);
    }
}
