package com.naver.financial.demo.dto.res;

import com.naver.financial.demo.domain.CouponGroup;
import com.naver.financial.demo.domain.CouponGroupStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CouponGroupResDto {

    private Long id;
    private String issuerId;
    private String code;
    private String name;
    private CouponGroupStatus status;
    private int amount;
    private int maxAmount;
    private int currentAmount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime validFromDt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime validToDt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public CouponGroupResDto(CouponGroup entity) {
        this.id = entity.getId();
        this.issuerId = entity.getIssuerId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.status = entity.getStatus();
        this.amount = entity.getAmount();
        this.maxAmount = entity.getMaxCount();
        this.currentAmount = entity.getCurrentCount();
        this.validFromDt = entity.getValidFromDt();
        this.validToDt = entity.getValidToDt();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
