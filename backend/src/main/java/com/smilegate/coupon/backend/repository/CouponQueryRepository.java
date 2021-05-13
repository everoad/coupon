package com.smilegate.coupon.backend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.smilegate.coupon.backend.core.utils.Querydsl4RepositorySupport;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponListDto;
import com.smilegate.coupon.backend.domain.dto.coupon.CouponSearchDto;
import com.smilegate.coupon.backend.domain.dto.coupon.QCouponListDto;
import com.smilegate.coupon.backend.domain.entity.Coupon;
import com.smilegate.coupon.backend.enums.MobileOSType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static com.smilegate.coupon.backend.domain.entity.QCoupon.coupon;

@Repository
public class CouponQueryRepository extends Querydsl4RepositorySupport {

    public CouponQueryRepository() {
        super(Coupon.class);
    }

    public Page<CouponListDto> findAll(CouponSearchDto couponSearchDto, Pageable pageable) {
        return applyPagination(pageable, query -> query.
                select(
                        new QCouponListDto(
                                myAs(coupon.code),
                                myAs(coupon.phoneNumber),
                                myAs(coupon.mobileOS),
                                myAs(coupon.createdTime)
                        )
                )
                .from(coupon)
                .where(
                        codeLike(couponSearchDto.getCode()),
                        phoneNumberEq(couponSearchDto.getPhoneNumber()),
                        mobileOSEq(couponSearchDto.getMobileOS())
                )
        );
    }

    private BooleanExpression codeLike(String code) {
        return StringUtils.hasText(code) ? coupon.code.contains(code) : null;
    }

    private BooleanExpression phoneNumberEq(String phoneNumber) {
        return StringUtils.hasText(phoneNumber) ? coupon.phoneNumber.eq(phoneNumber) : null;
    }

    private BooleanExpression mobileOSEq(MobileOSType osType) {
        return osType != null ? coupon.mobileOS.eq(osType) : null;
    }

}
