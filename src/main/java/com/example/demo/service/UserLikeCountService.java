package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLikeCountService {

    private final LikeRepository likeRepository;

    /**
     * ログイン中のユーザーが年間で獲得したいいねの数を取得する
     * @return 年間のいいね数
     */
    public long getAnnualLikeCount() {
        // 現在のユーザーのログインIDを取得
        String loginId = getCurrentUserLoginId();

        // 現在の年の初日と末日を取得
        Year currentYear = Year.now();
        LocalDateTime startOfYear = currentYear.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = currentYear.atMonth(12).atEndOfMonth().atTime(23, 59, 59);

        // 年間のいいね数を取得
        return likeRepository.countByLoginIdAndLikedAtBetween(loginId, startOfYear, endOfYear);
    }

    /**
     * ログイン中のユーザーが月間で獲得したいいねの数を取得する
     * @return 月間のいいね数
     */
    public long getMonthlyLikeCount() {
        // 現在のユーザーのログインIDを取得
        String loginId = getCurrentUserLoginId();

        // 現在の月の初日と末日を取得
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        // 月間のいいね数を取得
        return likeRepository.countByLoginIdAndLikedAtBetween(loginId, startOfMonth, endOfMonth);
    }

    /**
     * 現在ログイン中のユーザーのログインIDを取得する
     * @return ログインID
     */
    private String getCurrentUserLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
