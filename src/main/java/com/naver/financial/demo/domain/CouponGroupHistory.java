package com.naver.financial.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponGroupHistory {
    @Id
    @GeneratedValue
    @Column(name = "coupon_group_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_group_id", unique = true)
    private CouponGroup couponGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "json")
    private String data;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
