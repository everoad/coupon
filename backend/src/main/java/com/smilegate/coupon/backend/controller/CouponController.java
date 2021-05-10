package com.smilegate.coupon.backend.controller;

import com.smilegate.coupon.backend.core.dto.ApiResponseDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponInfoDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponListDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSaveDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSearchDto;
import com.smilegate.coupon.backend.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/coupons", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CouponController {


    private final CouponService couponService;


    @GetMapping
    public ApiResponseDto<Slice<CouponListDto>> findCouponList(CouponSearchDto couponSearchDto, Pageable pageable) {
        Slice<CouponListDto> couponSlice = couponService.findCouponList(couponSearchDto, pageable);
        return new ApiResponseDto<>(couponSlice);
    }


    @PostMapping
    public ApiResponseDto<CouponInfoDto> saveCoupon(@Validated @RequestBody CouponSaveDto couponSaveDto) {
        CouponInfoDto couponInfoDto = couponService.saveCoupon(couponSaveDto);
        return new ApiResponseDto<>(couponInfoDto);
    }


}
