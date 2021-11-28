package com.naver.financial.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int point;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CouponGroupHistory> couponGroupHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> couponList = new ArrayList<>();
}
