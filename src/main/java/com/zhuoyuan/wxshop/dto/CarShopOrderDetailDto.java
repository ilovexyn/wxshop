package com.zhuoyuan.wxshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarShopOrderDetailDto {
    private String imageUrl;
    private String goodName;
    private Integer num;
    private BigDecimal price;
}
