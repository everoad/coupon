package com.smilegate.coupon.backend.core.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.smilegate.coupon.backend.core.provider.CouponProvider;
import com.smilegate.coupon.backend.domain.entity.Coupon;
import com.smilegate.coupon.backend.enums.MobileOSType;
import com.smilegate.coupon.backend.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@EnableJpaAuditing
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(@Value("${spring.mvc.format.date}") String dateFormat,
                                                                @Value("${spring.mvc.format.date-time}") String dateTimeFormat) {
        return builder -> {
            // Request Body
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
            builder.deserializers(new LocalDateDeserializer(dateFormatter));
            builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
            // Response Body
            builder.serializers(new LocalDateSerializer(dateFormatter));
            builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
        };
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            CouponRepository couponRepository;

            @Autowired
            CouponProvider couponProvider;

            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {

                AtomicInteger number = new AtomicInteger(12341234);

                Map<String, String> map = IntStream.range(0, 100)
                        .mapToObj(i -> "010" + number.getAndIncrement())
                        .collect(Collectors.toMap(n -> couponProvider.createCode(n), n -> n));

                List<Coupon> list = map.keySet().stream()
                        .map((code) -> Coupon.builder()
                                .code(code)
                                .phoneNumber(map.get(code))
                                .mobileOS((int) (Math.random() * 10) % 2 == 0 ? MobileOSType.AOS : MobileOSType.IOS)
                                .build())
                        .collect(Collectors.toList());
                couponRepository.saveAll(list);
            }
        };
    }

}