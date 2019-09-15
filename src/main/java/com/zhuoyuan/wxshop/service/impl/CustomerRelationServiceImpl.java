package com.zhuoyuan.wxshop.service.impl;

import com.zhuoyuan.wxshop.model.CustomerRelation;
import com.zhuoyuan.wxshop.mapper.CustomerRelationMapper;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.CustomerRelationRecord;
import com.zhuoyuan.wxshop.request.PageRequest;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICustomerRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Result getCustomerRelationRecord(String hCustomer, Integer grade,Integer current,Integer size) throws Exception{
        PageRequest pageRequest = new PageRequest();
        pageRequest.setCurrent(current);
        pageRequest.setSize(size);
        CustomerRelationRecord customerRelationRecord = new CustomerRelationRecord();
        customerRelationRecord.setGrade(grade);
        customerRelationRecord.setHCustomer(hCustomer);
        List<UserInfo> userInfoList =  customerRelationMapper.getCustomerRelationRecord(customerRelationRecord);
        pageRequest.setTotal(userInfoList.size());
        List list = new ArrayList();
        int totalRecord = userInfoList.size();// 一共多少条记录
        if(totalRecord >0){
            int firstIndex = (current - 1) * size;
            if(firstIndex<totalRecord){
                int lastIndex = current * size;
                if(lastIndex > totalRecord){
                    lastIndex = totalRecord;
                }
                 list =userInfoList.subList(firstIndex,lastIndex);
            }

        }
        pageRequest.setRecords(list);
        return Result.success(pageRequest);
    }
}
