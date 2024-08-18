package com.example.demo.authentication;

import org.springframework.context.MessageSource;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository repository;
    private final MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = repository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        AppUtil.getMessage(messageSource, "login.error.invalid")));

        // ステータスがDENIEDの場合、DisabledExceptionをスロー
        if ("DENIED".equals(userInfo.getStatus())) {
            throw new DisabledException(
                    AppUtil.getMessage(messageSource, "login.error.denied"));
        }

        return User.withUsername(userInfo.getLoginId())
                .roles(userInfo.getRoles())
                .password(userInfo.getPass())
                .build();
    }
}
