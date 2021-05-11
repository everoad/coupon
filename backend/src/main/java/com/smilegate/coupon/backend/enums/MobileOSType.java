package com.smilegate.coupon.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MobileOSType {
    AOS,
    IOS;

    @JsonCreator
    public static MobileOSType from(String source) {
        return MobileOSType.valueOf(source.toUpperCase());
    }

}
