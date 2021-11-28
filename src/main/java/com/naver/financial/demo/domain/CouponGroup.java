package com.naver.financial.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CouponGroup extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_group_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String issuerId;

    @Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private CouponGroupStatus status;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int maxCount;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int currentCount;

    @Column(nullable = false)
    private LocalDateTime validFromDt;

    @Column(nullable = false)
    private LocalDateTime validToDt;

    @OneToMany(mappedBy = "couponGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CouponGroupHistory> couponGroupHistories = new ArrayList<>();

    @Builder
    public CouponGroup(Long id, String issuerId, String code, String name, CouponGroupStatus status, int amount, int maxCount, int currentCount, LocalDateTime validFromDt, LocalDateTime validToDt) {
        this.id = id;
        this.issuerId = issuerId;
        this.code = code;
        this.name = name;
        this.status = status;
        this.amount = amount;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.validFromDt = validFromDt;
        this.validToDt = validToDt;
    }

    public void update(String name, int amount, int maxCount, LocalDateTime validFromDt, LocalDateTime validToDt) {
        this.name = name;
        this.amount = amount;
        this.maxCount = maxCount;
        this.validFromDt = validFromDt;
        this.validToDt = validToDt;
    }

    public void publish() {
        this.status = CouponGroupStatus.PUBLISHED;
    }
    public void disable() {
        this.status = CouponGroupStatus.DISABLED;
    }
}
