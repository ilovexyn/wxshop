package com.zhuoyuan.wxshop.mapper;

import com.zhuoyuan.wxshop.model.UserAddress;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author Wangjie
 * @since 2019-07-08
 */
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    void updateState(UserAddress userAddress);
}
