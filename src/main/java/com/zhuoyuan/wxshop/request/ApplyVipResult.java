package com.zhuoyuan.wxshop.request;

import lombok.Data;

@Data
public class ApplyVipResult {
    private String openId;
    private Integer state;//0-不通过 1-通过
}
