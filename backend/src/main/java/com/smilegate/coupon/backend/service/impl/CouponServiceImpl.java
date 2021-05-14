package com.smilegate.coupon.backend.service.impl;

import com.smilegate.coupon.backend.core.provider.CouponProvider;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponInfoDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponListDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSaveDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSearchDto;
import com.smilegate.coupon.backend.domain.entity.Coupon;
import com.smilegate.coupon.backend.repository.CouponQueryRepository;
import com.smilegate.coupon.backend.repository.CouponRepository;
import com.smilegate.coupon.backend.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponProvider couponProvider;
    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;

    @Override
    public Page<CouponListDto> findCouponList(CouponSearchDto couponSearchDto, Pageable pageable) {
        return couponQueryRepository.findAll(couponSearchDto, pageable);
    }

    @Transactional
    @Override
    public CouponInfoDto saveCoupon(CouponSaveDto couponSaveDto) {
        if (!couponSaveDto.getAgree()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "개인정보 수집 및 이용에 대한 동의를 해주세요.");
        }
        if (couponRepository.existsByPhoneNumber(couponSaveDto.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사전예약을 신청하셨습니다.");
        }
        String couponCode = couponProvider.createNCheckCode(couponSaveDto.getPhoneNumber());
        Coupon coupon = couponSaveDto.toEntity(couponCode);
        couponRepository.save(coupon);
        return new CouponInfoDto(couponCode);
    }

}
