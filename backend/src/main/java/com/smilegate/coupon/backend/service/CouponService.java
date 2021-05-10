package com.smilegate.coupon.backend.service;

import com.smilegate.coupon.backend.domain.dto.coupon.CouponInfoDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponListDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSaveDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CouponService {

    Slice<CouponListDto> findCouponList(CouponSearchDto couponSearchDto, Pageable pageable);

    CouponInfoDto saveCoupon(CouponSaveDto couponSaveDto);

}
