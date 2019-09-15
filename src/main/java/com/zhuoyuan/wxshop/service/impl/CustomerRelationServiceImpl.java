package com.zhuoyuan.wxshop.service.impl;

import com.zhuoyuan.wxshop.model.CustomerRelation;
import com.zhuoyuan.wxshop.mapper.CustomerRelationMapper;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICustomerRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-09
 */
@Service
public class CustomerRelationServiceImpl extends ServiceImpl<CustomerRelationMapper, CustomerRelation> implements ICustomerRelationService {

    @Autowired
    CustomerRelationMapper customerRelationMapper;

    @Override
    public Result save(CustomerRelation customerRelation) throws Exception {

        customerRelation.setCt(new Date());
        customerRelation.setUt(new Date());
        customerRelationMapper.insert(customerRelation);

        return Result.success();
    }

    @Override
    public Result getCustomerRelationRecord(String hCustomer, Integer grade) throws Exception{
        Map map = new HashMap();
        map.put("hCustomer",hCustomer);
        map.put("grade",grade);
        List<UserInfo> userInfoList =  customerRelationMapper.getCustomerRelationRecord(map);
        return Result.success(userInfoList);
    }
}
