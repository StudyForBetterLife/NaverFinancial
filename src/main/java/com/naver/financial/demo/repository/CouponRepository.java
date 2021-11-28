package com.naver.financial.demo.repository;

import com.naver.financial.demo.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
