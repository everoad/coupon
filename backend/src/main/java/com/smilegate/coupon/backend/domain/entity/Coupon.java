package com.smilegate.coupon.backend.domain.entity;

import com.smilegate.coupon.backend.domain.entity.base.BaseTimeEntity;
import com.smilegate.coupon.backend.enums.MobileOSType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "UX_coupon_phone_number", columnList = "phone_number", unique = true),
                @Index(name = "IX_coupon_mobile_os", columnList = "mobile_os")
        }
)
public class Coupon extends BaseTimeEntity implements Persistable<String> {

    @Id
    @Column(name = "coupon_code", length = 12, nullable = false, updatable = false)
    private String code;

    @Column(name = "phone_number", length = 11, nullable = false, updatable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_os", length = 3, nullable = false)
    private MobileOSType mobileOS;

    @Builder
    public Coupon(String code, String phoneNumber, MobileOSType mobileOS) {
        Assert.hasText(code, "code is not empty.");
        Assert.state(code.length() == 12, "size of code must be 12.");
        Assert.hasText(phoneNumber, "phoneNumber is not empty.");
        Assert.state(10 <= phoneNumber.length() && phoneNumber.length() <= 11, "size of phoneNumber must be 10~11.");
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.mobileOS = mobileOS;
    }

    @Override
    public String getId() {
        return this.code;
    }

    @Override
    public boolean isNew() {
        return getCreatedTime() == null;
    }

}
