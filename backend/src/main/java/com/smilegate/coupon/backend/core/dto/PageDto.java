package com.smilegate.coupon.backend.core.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PageDto {

    private Integer page;
    private Integer size;
    private String sort;

    private static final String DESC = "descending";

    public Pageable getPageable() {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        if (StringUtils.hasText(sort)) {
            return PageRequest.of(page - 1, size, createSortObject());
        } else {
            return PageRequest.of(page - 1, size);
        }
    }

    private Sort createSortObject() {
        List<Sort.Order> orderList = new ArrayList<>();
        try {
            ObjectMapper om = new ObjectMapper();
            SortDto sortDto = om.readValue(sort, new TypeReference<>() {});
            if (DESC.equalsIgnoreCase(sortDto.getOrder())) {
                orderList.add(Sort.Order.desc(sortDto.getProp()));
            } else {
                orderList.add(Sort.Order.asc(sortDto.getProp()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Sort.by(orderList);
    }

    @Getter @Setter
    static class SortDto {
        private String order;
        private String prop;
    }

}
