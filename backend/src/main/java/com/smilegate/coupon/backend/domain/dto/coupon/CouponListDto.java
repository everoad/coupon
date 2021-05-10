package com.smilegate.coupon.backend.domain.dto.coupon;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CouponListDto {

    private String code;
    private String phoneNumber;
    private LocalDateTime createdTime;

    @QueryProjection
    public CouponListDto(String code, String phoneNumber, LocalDateTime createdTime) {
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.createdTime = createdTime;
    }

}
