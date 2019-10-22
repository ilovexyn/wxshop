package com.zhuoyuan.wxshop.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarShopPageRequest extends  PageRequest{
    private BigDecimal sum;
}
