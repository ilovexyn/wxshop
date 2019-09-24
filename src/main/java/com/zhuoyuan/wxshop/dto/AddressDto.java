package com.zhuoyuan.wxshop.dto;

import com.zhuoyuan.wxshop.model.UserAddress;
import lombok.Data;

import java.util.List;

@Data
public class AddressDto extends UserAddress {
    List<String> cityInfo;
}
