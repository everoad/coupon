package com.smilegate.coupon.backend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.smilegate.coupon.backend.core.utils.Querydsl4RepositorySupport;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponListDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSearchDto;
import com.smilegate.coupon.backend.domain.dto.coupon.QCouponListDto;
import com.smilegate.coupon.backend.domain.entity.Coupon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static com.smilegate.coupon.backend.domain.entity.QCoupon.coupon;

@Repository
public class CouponQueryRepository extends Querydsl4RepositorySupport {

    public CouponQueryRepository() {
        super(Coupon.class);
    }

    public Slice<CouponListDto> findAll(CouponSearchDto couponSearchDto, Pageable pageable) {
        return applyPaginationForSlice(pageable, query -> query.
                select(
                        new QCouponListDto(
                                coupon.code,
                                coupon.phoneNumber,
                                coupon.createdTime
                        )
                )
                .from(coupon)
                .where(
                        createdTimeBefore(couponSearchDto.getEndCreatedTime()),
                        phoneNumberEq(couponSearchDto.getPhoneNumber())
                )
                .orderBy(coupon.createdTime.desc())
        );
    }

    public BooleanExpression phoneNumberEq(String phoneNumber) {
        return StringUtils.hasText(phoneNumber) ? coupon.phoneNumber.eq(phoneNumber) : null;
    }

    public BooleanExpression createdTimeBefore(LocalDateTime endTime) {
        if (endTime == null) {
            endTime = LocalDateTime.now();
        }
        return coupon.createdTime.before(endTime);
    }


}
