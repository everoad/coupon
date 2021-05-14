package com.smilegate.coupon.backend.controller;

import com.smilegate.coupon.backend.core.dto.ApiResponseDto;
import com.smilegate.coupon.backend.core.dto.PageDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponInfoDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponListDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSaveDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSearchDto;
import com.smilegate.coupon.backend.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/coupons", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ApiResponseDto<Page<CouponListDto>> findCouponList(@Valid CouponSearchDto couponSearchDto, PageDto pageDto) {
        Page<CouponListDto> couponPage = couponService.findCouponList(couponSearchDto, pageDto.getPageable());
        return new ApiResponseDto<>(couponPage);
    }

    @PostMapping
    public ApiResponseDto<CouponInfoDto> saveCoupon(@Valid @RequestBody CouponSaveDto couponSaveDto) {
        CouponInfoDto couponInfoDto = couponService.saveCoupon(couponSaveDto);
        return new ApiResponseDto<>(couponInfoDto);
    }

}
