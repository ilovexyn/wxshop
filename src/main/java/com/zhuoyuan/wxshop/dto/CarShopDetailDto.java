package com.zhuoyuan.wxshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarShopDetailDto {
    private Long goodId;
    private String name;
    private String imageurl;
    private BigDecimal price;
    private Integer num;
}
