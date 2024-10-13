package com.example.demo.authentication;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//権限でURLを切り替えるための機能

@Component("customAuthenticationSuccessHandler")
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final HttpSession session;

    public CustomSuccessHandler(HttpSession session) {
        this.session = session;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "";

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                redirectUrl = "/admindashboard";
                break;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                redirectUrl = "/userdashboard";
                break;
            }
        }

        if (redirectUrl.isEmpty()) {
            throw new IllegalStateException();
        }

        // ユーザー名をセッションに保存
        session.setAttribute("loginId", authentication.getName());

        response.sendRedirect(redirectUrl);
    }
}
