package com.zhuoyuan.wxshop.service;

import com.zhuoyuan.wxshop.dto.AddressDto;
import com.zhuoyuan.wxshop.model.UserAddress;
import com.baomidou.mybatisplus.service.IService;
import com.zhuoyuan.wxshop.request.Result;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author Wangjie
 * @since 2019-07-08
 */
public interface IUserAddressService extends IService<UserAddress> {

    Result updateState(UserAddress userAddress);

    void saveAddress(UserAddress userAddress)throws  Exception;

    AddressDto selectAddressById(Long id);
}
