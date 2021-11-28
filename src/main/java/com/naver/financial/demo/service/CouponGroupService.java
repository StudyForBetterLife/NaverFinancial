package com.naver.financial.demo.service;

import com.naver.financial.demo.domain.CouponGroup;
import com.naver.financial.demo.domain.CouponGroupStatus;
import com.naver.financial.demo.dto.ErrorDto;
import com.naver.financial.demo.dto.ResponseDto;
import com.naver.financial.demo.dto.req.CouponGroupCreateReqDto;
import com.naver.financial.demo.dto.req.CouponGroupUpdateReqDto;
import com.naver.financial.demo.dto.res.CouponGroupCreateResDto;
import com.naver.financial.demo.dto.res.CouponGroupResDto;
import com.naver.financial.demo.repository.CouponGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CouponGroupService {

    private final CouponGroupRepository couponGroupRepository;

    @Transactional
    public ResponseDto<CouponGroupCreateResDto> save(String code,
                                                     String xUserId,
                                                     CouponGroupCreateReqDto reqDto) {

        if (!codeValid(code)) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "잘못된 요청입니다.")
            );
        }

        if (couponGroupRepository.existsByCode(code)) {
            return new ResponseDto<>(false,
                    null,
                    new ErrorDto(400, "중복된 코드입니다."));
        }

        CouponGroup couponGroup = CouponGroup.builder()
                .name(reqDto.getName())
                .amount(reqDto.getAmount())
                .maxCount(reqDto.getMaxCount())
                .issuerId(xUserId)
                .code(code)
                .validFromDt(reqDto.getValidFromDt())
                .validToDt(reqDto.getValidToDt())
                .status(CouponGroupStatus.CREATED)
                .build();

        CouponGroup save = couponGroupRepository.save(couponGroup);
        return new ResponseDto<>(
                false,
                new CouponGroupCreateResDto(save),
                null
        );
    }

    @Transactional
    public ResponseDto<CouponGroupResDto> update(String code,
                                                 String issuer_id,
                                                 CouponGroupUpdateReqDto reqDto) {

        CouponGroup couponGroup = valid(code);
        if (couponGroup == null) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "잘못된 요청입니다.")
            );
        }
        CouponGroupStatus status = couponGroup.getStatus();
        if (!status.equals(CouponGroupStatus.CREATED)) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "CREATED 상태의 코드만 수정 가능합니다.")
            );
        }

        String issuerId = couponGroup.getIssuerId();
        if (!issuerId.equals(issuer_id)) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(404, "X-USER-ID 값 != 쿠폰의 issuer_id 값")
            );
        }

        couponGroup.update(
                reqDto.getName(),
                reqDto.getAmount(),
                reqDto.getMaxCount(),
                reqDto.getValidFromDt(),
                reqDto.getValidToDt()
        );

        return new ResponseDto<>(
                true,
                new CouponGroupResDto(couponGroup),
                null
        );

    }

    @Transactional
    public ResponseDto<CouponGroupResDto> publish(String code, String issuer_id) {
        CouponGroup couponGroup = valid(code);
        if (couponGroup == null) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "잘못된 요청입니다.")
            );
        }
        CouponGroupStatus status = couponGroup.getStatus();
        LocalDateTime validToDt = couponGroup.getValidToDt();
        if ((!status.equals(CouponGroupStatus.CREATED)) && (validToDt.isAfter(LocalDateTime.now()))) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "CREATED 상태이고 유효기간이 만료되지 않은 쿠폰 그룹만 발행할 수 있습니다")
            );
        }

        String issuerId = couponGroup.getIssuerId();
        if (!issuerId.equals(issuer_id)) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(404, "X-USER-ID 값 != 쿠폰의 issuer_id 값")
            );
        }

        couponGroup.publish();

        return new ResponseDto<>(
                true,
                new CouponGroupResDto(couponGroup),
                null
        );

    }

    private boolean codeValid(String code) {
        return code.length() >= 2 && code.length() <= 50;
    }

    private CouponGroup valid(String code) {
        if (!codeValid(code)) {
            return null;
        }
        Optional<CouponGroup> optional = couponGroupRepository.findCouponGroupByCode(code);
        return optional.orElse(null);
    }

    @Transactional
    public ResponseDto<CouponGroupResDto> disable(String code, String issuer_id) {
        CouponGroup couponGroup = valid(code);
        if (couponGroup == null) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "잘못된 요청입니다.")
            );
        }
        CouponGroupStatus status = couponGroup.getStatus();
        LocalDateTime validToDt = couponGroup.getValidToDt();
        if ((!status.equals(CouponGroupStatus.CREATED)) && (validToDt.isAfter(LocalDateTime.now()))) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(400, "CREATED 상태이고 유효기간이 만료되지 않은 쿠폰 그룹만 발행할 수 있습니다")
            );
        }

        String issuerId = couponGroup.getIssuerId();
        if (!issuerId.equals(issuer_id)) {
            return new ResponseDto<>(
                    false,
                    null,
                    new ErrorDto(404, "X-USER-ID 값 != 쿠폰의 issuer_id 값")
            );
        }

        couponGroup.disable();

        return new ResponseDto<>(
                true,
                new CouponGroupResDto(couponGroup),
                null
        );
    }
}
