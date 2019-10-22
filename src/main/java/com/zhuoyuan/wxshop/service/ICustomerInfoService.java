package com.zhuoyuan.wxshop.service;

import com.zhuoyuan.wxshop.model.CustomerInfo;
import com.baomidou.mybatisplus.service.IService;
import com.zhuoyuan.wxshop.request.ApplyVipResult;
import com.zhuoyuan.wxshop.request.Result;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-06
 */
public interface ICustomerInfoService extends IService<CustomerInfo> {

    Result save(CustomerInfo customerInfo)throws Exception;

    Result applyVip(CustomerInfo customerInfo)throws Exception;

    /**
     * 会员申请结果通知·
     * @param openId
     * @param state
     * @return
     * @throws Exception
     */
    Result applyVipResult(String openId,Integer state)throws Exception;
}
