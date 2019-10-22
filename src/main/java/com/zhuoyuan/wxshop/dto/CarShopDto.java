package com.zhuoyuan.wxshop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarShopDto {
    private List<CarShopDetailDto> carShopDetailDtoList;
    private BigDecimal sum;
}
