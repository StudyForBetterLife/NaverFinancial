package com.naver.financial.demo.coupon;

import com.naver.financial.demo.dto.ResponseDto;
import com.naver.financial.demo.dto.res.CouponResDto;
import com.naver.financial.demo.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * 5. 쿠폰 다운로드
     */
    @PostMapping("/{code}/download")
    public ResponseDto<CouponResDto> download(@PathVariable String code,
                                              @RequestHeader(value = "X-USER-ID") String user_id) {
        return couponService.download(code, user_id);
    }

    /**
     * 6. 쿠폰 사용
     */

    /**
     * 7. 쿠폰 목록 조회
     */

}
