package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String loginId;
    private String userName;
    private String base64Image;
    private long likeCount;
    private boolean likedByCurrentUser;
}
