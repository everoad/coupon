package com.smilegate.coupon.backend.service.impl;

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
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {


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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
        if (couponRepository.existsByPhoneNumber(couponSaveDto.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사전예약을 신청하셨습니다.");
        }
        String couponCode = getCouponCode(couponSaveDto.getPhoneNumber());
        Coupon coupon = couponSaveDto.toEntity(couponCode);
        couponRepository.save(coupon);
        return new CouponInfoDto(couponCode);
    }


    private String getCouponCode(String phoneNumber) {
        String couponCode = generateCoupon(phoneNumber);
        if (couponRepository.existsById(couponCode)) {
            return generateCoupon(phoneNumber);
        }
        return couponCode;
    }

    SecureRandom random = new SecureRandom();

    private String generateCoupon(String phoneNumber) {
        byte[] randomBytes = new byte[6];

        String seed = UUID.randomUUID() + phoneNumber;
        random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        random.nextBytes(randomBytes);
        return byteArrayToHex(randomBytes);
    }

    private String byteArrayToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (final byte b : bytes) {
            builder.append(String.format("%02x", b & 0xff).toUpperCase());
        }
        return builder.toString();
    }


}
