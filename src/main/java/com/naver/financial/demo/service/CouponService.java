package com.naver.financial.demo.service;

import com.naver.financial.demo.domain.CouponGroup;
import com.naver.financial.demo.domain.CouponGroupStatus;
import com.naver.financial.demo.dto.ErrorDto;
import com.naver.financial.demo.dto.ResponseDto;
import com.naver.financial.demo.dto.res.CouponResDto;
import com.naver.financial.demo.repository.CouponGroupRepository;
import com.naver.financial.demo.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponGroupRepository couponGroupRepository;

    @Transactional
    public ResponseDto<CouponResDto> download(String code, String user_id) {
        if (!codeValid(code)) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "잘못된 요청입니다.")
            );
        }
        Optional<CouponGroup> optional = couponGroupRepository.findCouponGroupByCode(code);
        if (!optional.isPresent()) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(404, "존재하지 않는 코드입니다.")
            );
        }
        CouponGroup couponGroup = optional.get();
        CouponGroupStatus status = couponGroup.getStatus();
        LocalDateTime validToDt = couponGroup.getValidToDt();

        if ((!status.equals(CouponGroupStatus.PUBLISHED)) && validToDt.isAfter(LocalDateTime.now())) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "잘못된 요청입니다.")
            );
        }


        return null;
    }

    private boolean codeValid(String code) {
        return code.length() >= 2 && code.length() <= 50;
    }

}
