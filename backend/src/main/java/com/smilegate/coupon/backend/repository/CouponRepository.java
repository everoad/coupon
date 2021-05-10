package com.smilegate.coupon.backend.repository;

import com.smilegate.coupon.backend.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, String> {

    boolean existsByPhoneNumber(String phoneNumber);

}