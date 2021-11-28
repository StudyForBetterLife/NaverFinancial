package com.naver.financial.demo.coupongroup;

import com.naver.financial.demo.dto.ErrorDto;
import com.naver.financial.demo.dto.ResponseDto;
import com.naver.financial.demo.dto.req.CouponGroupCreateReqDto;
import com.naver.financial.demo.dto.req.CouponGroupUpdateReqDto;
import com.naver.financial.demo.dto.res.CouponGroupCreateResDto;
import com.naver.financial.demo.dto.res.CouponGroupResDto;
import com.naver.financial.demo.service.CouponGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-groups")
@Slf4j
public class CouponGroupController {

    private final CouponGroupService couponGroupService;

    /**
     * 1. 쿠폰 그룹 생성
     */
    @PostMapping("/{code}")
    public ResponseDto<CouponGroupCreateResDto> create(@PathVariable String code,
                                                       @RequestBody CouponGroupCreateReqDto reqDto,
                                                       @RequestHeader(value = "X-USER-ID") String issuer_id) {

        String name = reqDto.getName();
        int amount = reqDto.getAmount();
        int maxAmount = reqDto.getMaxCount();
        LocalDateTime validFromDt = reqDto.getValidFromDt();
        LocalDateTime validToDt = reqDto.getValidToDt();

        if (name.length() >= 2 && name.length() <= 50
                && amount >= 1 && maxAmount >= 1
                && validFromDt.isBefore(validToDt)
                && validToDt.isAfter(LocalDateTime.now())
        ) {
            return couponGroupService.save(code, issuer_id, reqDto);
        }

        return new ResponseDto<>(
                false,
                null,
                new ErrorDto(400, "잘못된 요청입니다.")
        );
    }

    /**
     * 2. 쿠폰 그룹 수정
     */
    @PutMapping("/{code}")
    public ResponseDto<CouponGroupResDto> update(@PathVariable String code,
                                                       @RequestBody CouponGroupUpdateReqDto reqDto,
                                                       @RequestHeader(value = "X-USER-ID") String issuer_id) {

        String name = reqDto.getName();
        int amount = reqDto.getAmount();
        int maxAmount = reqDto.getMaxCount();
        LocalDateTime validFromDt = reqDto.getValidFromDt();
        LocalDateTime validToDt = reqDto.getValidToDt();

        return couponGroupService.update(code, issuer_id, reqDto);
    }
    /**
     * 3. 쿠폰 그룹 발행
     */

    @PostMapping("/{code}/publish")
    public ResponseDto<CouponGroupResDto> publish(@PathVariable String code,
                                                  @RequestHeader(value = "X-USER-ID") String issuer_id) {

        return couponGroupService.publish(code, issuer_id);
    }

    /**
     * 4. 쿠폰 그룹 비활성화
     */

    @PostMapping("/{code}/disable")
    public ResponseDto<CouponGroupResDto> disable(@PathVariable String code,
                                                  @RequestHeader(value = "X-USER-ID") String issuer_id) {

        return couponGroupService.disable(code, issuer_id);
    }

}
