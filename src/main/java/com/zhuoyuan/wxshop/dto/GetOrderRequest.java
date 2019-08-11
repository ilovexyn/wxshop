package com.zhuoyuan.wxshop.dto;

import com.zhuoyuan.wxshop.model.OrderRecords;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderRequest extends OrderRecords {

    private Integer goodsCount;
    private List<GetOrderDetailRequest> getOrderDetailRequestList;
}
