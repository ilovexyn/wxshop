package com.zhuoyuan.wxshop.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private String openid;
    private Long addressId;
    private Long goodsId;
    private Integer num;
}
