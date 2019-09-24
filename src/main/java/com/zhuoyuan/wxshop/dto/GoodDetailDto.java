package com.zhuoyuan.wxshop.dto;

import com.zhuoyuan.wxshop.model.Goods;
import lombok.Data;

import java.util.List;

@Data
public class GoodDetailDto extends Goods {
    List<String> images;
}
