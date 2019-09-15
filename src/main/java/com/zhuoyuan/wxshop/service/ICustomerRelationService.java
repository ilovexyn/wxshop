package com.zhuoyuan.wxshop.service;

import com.zhuoyuan.wxshop.model.CustomerRelation;
import com.baomidou.mybatisplus.service.IService;
import com.zhuoyuan.wxshop.request.Result;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-09
 */
public interface ICustomerRelationService extends IService<CustomerRelation> {

    Result save(CustomerRelation customerRelation) throws Exception;

    Result getCustomerRelationRecord(String hCustomer,Integer grade)throws Exception;
}
