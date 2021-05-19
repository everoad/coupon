package com.smilegate.coupon.backend.core.provider;

import com.smilegate.coupon.backend.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class CouponProvider {

    private final CouponRepository couponRepository;

    public String createNCheckCode(String phoneNumber) {
        String couponCode = createCode(phoneNumber);
        if (couponRepository.existsById(couponCode)) {
            return createNCheckCode(phoneNumber);
        }
        return couponCode;
    }

    public String createCode(String phoneNumber) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[6];
        String seed = System.currentTimeMillis() + phoneNumber;
        random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        random.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(16).toUpperCase();
    }

}
