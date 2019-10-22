package com.zhuoyuan.wxshop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarShopOrderDto  {
    private BigDecimal sumPrice;
    private List<CarShopOrderDetailDto> carShopOrderDetailDtoList;
}
