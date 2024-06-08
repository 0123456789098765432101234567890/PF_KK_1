package com.example.demo.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.constant.AuthorityKind;

@Service
public class AsyncMenuService {

    @Async
    public CompletableFuture<Boolean> hasUserManageAuth(User user) {
        var hasUserManageAuth = user.getAuthorities().stream()
                .allMatch(authority -> authority.getAuthority()
                        .equals(AuthorityKind.ITEM_AND_USER_MANAGER.getAuthorityKind()));
        return CompletableFuture.completedFuture(hasUserManageAuth);
    }
}
