package com.zhuoyuan.wxshop.request;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Data
public class PageRequest {

    private Integer total;
    private Integer size;
    private Integer pages;
    private Integer current;
    private List<T> records;
}
