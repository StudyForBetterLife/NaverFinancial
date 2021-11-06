package com.naver.financial.demo.coupon;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import com.naver.financial.demo.BaseTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CouponControllerTest extends BaseTest {

    public CouponControllerTest() {
        super("/coupons");
    }

    @Nested
    @DisplayName("7. 보유 쿠폰 목록 조회")
    class GetAllCoupons {
        @Test
        @DisplayName("실패 - 권한 없음 (w/o X-USER-ID)")
        void fobiddenWithoutUserId() throws Exception {
            failure(reqGet("/"), HttpStatus.FORBIDDEN);
        }

        @Test
        @DisplayName("성공")
        void successDefault() throws Exception {
            success(reqGet("/", "user3"))
                    .andExpect(jsonPath("$.response").isArray())
                    .andExpect(jsonPath("$.response.length()", is(5)))
                    .andExpect(jsonPath("$.response[0].id", is(1)))
                    .andExpect(jsonPath("$.response[0].user_id", is("user3")))
            ;
        }
    }
}
