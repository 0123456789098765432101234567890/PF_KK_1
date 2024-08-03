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

@Service
@RequiredArgsConstructor
public class LikeRankingPublicService {

    private final UserInfoRepository userInfoRepository;
    private final LikeRepository likeRepository;

    public List<UserInfo> getMonthlyRanking() {
        // 現在の月の初日と末日を取得
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        // 今月のいいね数をカウントしてユーザーリストを取得
        List<UserInfo> users = userInfoRepository.findByRoles("USER").stream()
                .map(user -> {
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    // 今月のいいね数を設定
                    long likeCount = likeRepository.countByLoginIdAndLikedAtBetween(
                            user.getLoginId(), startOfMonth, endOfMonth);
                    user.setLikeCount(likeCount);
                    return user;
                })
                // いいね数で降順ソート
                .sorted((u1, u2) -> Long.compare(u2.getLikeCount(), u1.getLikeCount()))
                .collect(Collectors.toList());

        return users;
    }
}
