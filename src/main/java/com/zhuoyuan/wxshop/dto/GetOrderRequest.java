package com.zhuoyuan.wxshop.dto;

import com.zhuoyuan.wxshop.model.OrderRecords;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderRequest extends OrderRecords {

    private Integer goodsCount;
    private Integer state;
    private List<GetOrderDetailRequest> getOrderDetailRequestList;
    private String addressInfo;
}
