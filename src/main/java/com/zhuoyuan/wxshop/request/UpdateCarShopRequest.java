package com.zhuoyuan.wxshop.request;

import com.zhuoyuan.wxshop.model.CarShop;
import lombok.Data;

@Data
public class UpdateCarShopRequest extends CarShop {
    private Integer type;// -1 1
}
