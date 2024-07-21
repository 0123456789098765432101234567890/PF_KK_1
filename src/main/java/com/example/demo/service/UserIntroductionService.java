package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserIntroductionService {

    private final UserInfoRepository userInfoRepository;

    @Transactional(readOnly = true)
    public List<UserInfo> getUserList() {
        return userInfoRepository.findByRoles("USER");
    }
}
