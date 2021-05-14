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
    private final SecureRandom random = new SecureRandom();

    public String createNCheckCode(String phoneNumber) {
        String couponCode = createCode(phoneNumber);
        if (couponRepository.existsById(couponCode)) {
            return createCode(phoneNumber);
        }
        return couponCode;
    }

    public String createCode(String phoneNumber) {
        byte[] randomBytes = new byte[6];
        String seed = System.currentTimeMillis() + phoneNumber;
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
