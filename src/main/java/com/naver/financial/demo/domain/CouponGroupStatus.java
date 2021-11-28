package com.naver.financial.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponGroupStatus {
    CREATED,
    PUBLISHED,
    DISABLED;

}
