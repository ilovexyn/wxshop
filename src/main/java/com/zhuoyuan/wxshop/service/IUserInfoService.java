package com.zhuoyuan.wxshop.service;

import com.zhuoyuan.wxshop.model.UserInfo;
import com.baomidou.mybatisplus.service.IService;
import com.zhuoyuan.wxshop.request.Result;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
public interface IUserInfoService extends IService<UserInfo> {

    Result getWxuser(String code);

    Result save(UserInfo userInfo);
}
