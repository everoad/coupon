package com.smilegate.coupon.backend.domain.dto.coupon;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CouponInfoDto {

    private String code;

    public CouponInfoDto(String code) {
        this.code = code;
    }
}
