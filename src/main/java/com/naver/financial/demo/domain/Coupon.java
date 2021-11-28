package com.naver.financial.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private CouponStatus status;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime validFromDt;

    @Column(nullable = false)
    private LocalDateTime validToDt;

    private LocalDateTime usedAt;
}
