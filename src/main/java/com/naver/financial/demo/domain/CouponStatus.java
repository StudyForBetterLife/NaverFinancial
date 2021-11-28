package com.naver.financial.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponStatus {
    ISSUED, USED, EXPIRED;
}
