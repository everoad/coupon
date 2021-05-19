package com.smilegate.coupon.backend.core.provider;

import com.smilegate.coupon.backend.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return convertByteArrayToBase32(randomBytes);
    }

    private String convertByteArrayToBase32(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (final byte b : bytes) {
            String str = Integer.toString(b & 0xff, 32);
            str = Math.round(Math.random()) > 0 ? str.toUpperCase() : str.toLowerCase();
            if (str.length() < 2) {
                builder.append("0").append(str);
            } else {
                builder.append(str);
            }
        }
        return builder.toString();
    }

}
