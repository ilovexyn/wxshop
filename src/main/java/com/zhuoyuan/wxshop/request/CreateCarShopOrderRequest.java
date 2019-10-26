package com.zhuoyuan.wxshop.request;

import com.zhuoyuan.wxshop.dto.CarShopDetailDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateCarShopOrderRequest {
   private  BigDecimal sum;
   private List<CarShopDetailDto> carShopDetailDtoList;
}
