package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.Year;
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

    /**
     * 年間いいねランキングを取得します
     * @return 年間ランキング上位5ユーザー
     */
    public List<UserInfo> getTop5UsersForYear() {
        // 現在の年の初日と末日を取得
        Year currentYear = Year.now();
        LocalDateTime startOfYear = currentYear.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = currentYear.atMonth(12).atEndOfMonth().atTime(23, 59, 59);

        log.info("年間いいねランキングを計算中です。期間: {} から {}", startOfYear, endOfYear);

        return userInfoRepository.findByRoles("USER").stream()
                .map(user -> {
                    // プロフィール画像をBase64に変換して設定
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    // 年間のいいね数を取得して設定
                    long likeCount = likeRepository.countByLoginIdAndLikedAtBetween(user.getLoginId(), startOfYear, endOfYear);
                    user.setLikeCount(likeCount);
                    log.info("ユーザー: {}, 年間いいね数: {}", user.getLoginId(), likeCount);
                    return user;
                })
                // いいね数で降順ソート
                .sorted((u1, u2) -> Long.compare(u2.getLikeCount(), u1.getLikeCount()))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * 月間いいねランキングを取得します
     * @return 月間ランキング上位5ユーザー
     */
    public List<UserInfo> getTop5UsersForMonth() {
        // 現在の月の初日と末日を取得
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        log.info("月間いいねランキングを計算中です。期間: {} から {}", startOfMonth, endOfMonth);

        return userInfoRepository.findByRoles("USER").stream()
                .map(user -> {
                    // プロフィール画像をBase64に変換して設定
                    if (user.getProfImg() != null) {
                        user.setBase64Image(Base64.getEncoder().encodeToString(user.getProfImg()));
                    }
                    // 月間のいいね数を取得して設定
                    long likeCount = likeRepository.countByLoginIdAndLikedAtBetween(user.getLoginId(), startOfMonth, endOfMonth);
                    user.setLikeCount(likeCount);
                    log.info("ユーザー: {}, 月間いいね数: {}", user.getLoginId(), likeCount);
                    return user;
                })
                // いいね数で降順ソート
                .sorted((u1, u2) -> Long.compare(u2.getLikeCount(), u1.getLikeCount()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
