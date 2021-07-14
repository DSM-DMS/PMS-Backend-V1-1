package com.dms.pms.entity.pms.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {
    FACEBOOK,
    APPLE,
    KAKAO,
    NAVER,
    LOCAL;
}
