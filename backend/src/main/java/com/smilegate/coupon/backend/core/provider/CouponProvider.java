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
        byte[] randomBytes = new byte[10];
        String seed = System.currentTimeMillis() + phoneNumber;
        random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        random.nextBytes(randomBytes);
        return byteArrayToBase32(randomBytes);
    }

    public String byteArrayToBase32(byte[] data) {
        StringBuilder result = new StringBuilder();
        byte[] bits = new byte[data.length * 8];
        for (int i = 0; i < data.length; i++) {
            bits[i * 8] = (byte) ((data[i] & 0x80) >> 7);
            bits[i * 8 + 1] = (byte) ((data[i] & 0x40) >> 6);
            bits[i * 8 + 2] = (byte) ((data[i] & 0x20) >> 5);
            bits[i * 8 + 3] = (byte) ((data[i] & 0x10) >> 4);
            bits[i * 8 + 4] = (byte) ((data[i] & 0x08) >> 3);
            bits[i * 8 + 5] = (byte) ((data[i] & 0x04) >> 2);
            bits[i * 8 + 6] = (byte) ((data[i] & 0x02) >> 1);
            bits[i * 8 + 7] = (byte) ((data[i] & 0x01));
        }
        for (int i = 0; i < data.length / 5 * 8 - 4; i++) {
            boolean isUpper = Math.round(Math.random()) > 0;
            String str = "";
            byte value = (byte) (bits[i * 5] << 4
                    | bits[i * 5 + 1] << 3 | bits[i * 5 + 2] << 2
                    | bits[i * 5 + 3] << 1 | bits[i * 5 + 4]);
            if (value >= 0 && value < 26) {
                str += (char) (value + 'A');
            }
            if (value >= 26 && value < 30) {
                str += (char) (value - 26 + '2');
            }
            if (value == 30) {
                str += '7';
            }
            if (value == 31) {
                str += '9';
            }
            result.append(isUpper ? str.toUpperCase() : str.toLowerCase());
        }
        return result.toString();
    }

}
