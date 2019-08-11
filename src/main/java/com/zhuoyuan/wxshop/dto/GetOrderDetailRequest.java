package com.zhuoyuan.wxshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetOrderDetailRequest {

    private String imageUrl;
    private String name;
    private BigDecimal price;
    private Integer count;
}
