package com.smilegate.coupon.backend.domain.dto.coupon;

import com.smilegate.coupon.backend.domain.entity.Coupon;
import com.smilegate.coupon.backend.enums.MobileOSType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CouponSaveDto {

    @Pattern(regexp = "^[0-9].{9,10}$", message = "핸드폰 번호를 입력해 주세요.")
    private String phoneNumber;

    @NotNull(message = "개인정보 수집 및 이용에 대한 동의를 해야 합니다.")
    private Boolean agree;

    @NotNull(message = "사용하시는 핸드폰 OS를 선택해 주세요.")
    private MobileOSType mobileOS;

    public Coupon toEntity(String code) {
        return Coupon.builder()
                .code(code)
                .phoneNumber(phoneNumber)
                .mobileOS(mobileOS)
                .build();
    }

}
