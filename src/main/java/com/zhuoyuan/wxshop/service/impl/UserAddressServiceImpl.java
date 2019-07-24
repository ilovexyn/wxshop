package com.zhuoyuan.wxshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.UserAddress;
import com.zhuoyuan.wxshop.mapper.UserAddressMapper;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IUserAddressService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author Wangjie
 * @since 2019-07-08
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

    @Autowired
    UserAddressMapper userAddressMapper;

    @Override
    @Transactional
    public Result updateState(UserAddress userAddress) {
       //更新其他为1
        UserAddress combine = new UserAddress();
        combine.setState(2);
        UserAddress muserAddress = userAddressMapper.selectOne(combine);
        muserAddress.setState(1);
        userAddressMapper.updateById(muserAddress);
       //更改目标为2
        UserAddress tuserAddress = userAddressMapper.selectById(userAddress.getId());
        tuserAddress.setState(2);
        tuserAddress.setUt(new Date());
        userAddressMapper.updateById(tuserAddress);

        return Result.success();
    }
}
