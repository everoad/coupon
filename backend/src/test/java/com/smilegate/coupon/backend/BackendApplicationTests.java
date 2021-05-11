package com.smilegate.coupon.backend;

import com.smilegate.coupon.backend.domain.entity.Coupon;
import com.smilegate.coupon.backend.enums.MobileOSType;
import com.smilegate.coupon.backend.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    CouponRepository couponRepository;

    SecureRandom random = new SecureRandom();

    @Rollback(false)
    @Test
    void contextLoads() throws UnsupportedEncodingException {
        AtomicInteger number = new AtomicInteger(33677270);
        List<Coupon> list = IntStream.range(0, 100).mapToObj(i -> {
            String phoneNumber = "010" + number.getAndIncrement();
            String s = generateCoupon(phoneNumber);
            return Coupon.builder()
                    .code(s)
                    .phoneNumber(phoneNumber)
                    .mobileOS(MobileOSType.AOS)
                    .build();
        }).collect(Collectors.toList());
        couponRepository.saveAll(list);
    }



    String generateCoupon(String phoneNumber) {
        byte[] randomBytes = new byte[6];
        String seed = System.currentTimeMillis() + phoneNumber;
        random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        random.nextBytes(randomBytes);
        System.out.println("new String(randomBytes) = " + new String(randomBytes));
        return byteArrayToHex(randomBytes);
    }

    String byteArrayToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (final byte b : bytes) {
            builder.append(String.format("%02x", b & 0xff).toUpperCase());
        }
        return builder.toString();
    }

}
