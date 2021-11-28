package com.naver.financial.demo.repository;

import com.naver.financial.demo.domain.CouponGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponGroupRepository extends JpaRepository<CouponGroup, Long> {

    boolean existsByCode(String code);

    Optional<CouponGroup> findCouponGroupByCode(String code);
}
