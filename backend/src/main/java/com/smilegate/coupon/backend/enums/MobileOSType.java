package com.smilegate.coupon.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MobileOSType {
    AOS,
    IOS;

    @JsonCreator
    public static MobileOSType from(String source) {
        try {
            return MobileOSType.valueOf(source.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }

}
