package com.smilegate.coupon.backend.domain.dto.coupon;

import com.smilegate.coupon.backend.enums.MobileOSType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouponSearchDto {

    private String code;

    private String phoneNumber;

    private MobileOSType mobileOS;

    private LocalDateTime endCreatedTime;

}
