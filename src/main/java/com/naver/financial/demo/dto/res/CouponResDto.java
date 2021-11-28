package com.naver.financial.demo.dto.res;

import com.naver.financial.demo.domain.Coupon;
import com.naver.financial.demo.domain.CouponStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CouponResDto {
    private Long id;
    private String userId;
    private String code;
    private String name;
    private CouponStatus status;
    private int amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime validFromDt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime validToDt;

    private LocalDateTime usedAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public CouponResDto(Coupon entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.status = entity.getStatus();
        this.amount = entity.getAmount();
        this.validFromDt = entity.getValidFromDt();
        this.validToDt = entity.getValidToDt();
        this.usedAt = entity.getUsedAt();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
