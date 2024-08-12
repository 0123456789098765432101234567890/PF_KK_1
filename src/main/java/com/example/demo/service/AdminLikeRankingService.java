package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminLikeRankingService {

    private final UserInfoRepository userInfoRepository;
    private final LikeRepository likeRepository;

    public List<UserInfo> getTop5UsersForMonth() {
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        log.info("Calculating LikeCount for the month, From: {}, To: {}", startOfMonth, endOfMonth);

        return userInfoRepository.findByRoles("USER").stream()  // USER権限のアカウントを対象にする
                .map(user -> {
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    long likeCount = likeRepository.countByLoginIdAndLikedAtBetween(user.getLoginId(), startOfMonth, endOfMonth);
                    user.setLikeCount(likeCount);
                    log.info("User: {}, LikeCount for Month: {}", user.getLoginId(), likeCount);
                    return user;
                })
                .sorted((u1, u2) -> Long.compare(u2.getLikeCount(), u1.getLikeCount()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<UserInfo> getTop5UsersForWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = now.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59);

        log.info("Calculating LikeCount for the week, From: {}, To: {}", startOfWeek, endOfWeek);

        return userInfoRepository.findByRoles("USER").stream()  // USER権限のアカウントを対象にする
                .map(user -> {
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    long likeCount = likeRepository.countByLoginIdAndLikedAtBetween(user.getLoginId(), startOfWeek, endOfWeek);
                    user.setLikeCount(likeCount);
                    log.info("User: {}, LikeCount for Week: {}", user.getLoginId(), likeCount);
                    return user;
                })
                .sorted((u1, u2) -> Long.compare(u2.getLikeCount(), u1.getLikeCount()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
