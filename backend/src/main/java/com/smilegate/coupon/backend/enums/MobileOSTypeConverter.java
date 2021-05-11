package com.smilegate.coupon.backend.enums;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MobileOSTypeConverter implements Converter<String, MobileOSType> {

    @Override
    public MobileOSType convert(String source) {
        return MobileOSType.valueOf(source.toUpperCase());
    }



}
