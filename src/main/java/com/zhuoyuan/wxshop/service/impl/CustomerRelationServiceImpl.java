package com.zhuoyuan.wxshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.mapper.CustomerInfoMapper;
import com.zhuoyuan.wxshop.mapper.OrderRecordsMapper;
import com.zhuoyuan.wxshop.mapper.UserInfoMapper;
import com.zhuoyuan.wxshop.model.CustomerInfo;
import com.zhuoyuan.wxshop.model.CustomerRelation;
import com.zhuoyuan.wxshop.mapper.CustomerRelationMapper;
import com.zhuoyuan.wxshop.model.OrderRecords;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.CustomerRelationRecord;
import com.zhuoyuan.wxshop.request.PageRequest;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICustomerRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.status.GoodsStatus;
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
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    OrderRecordsMapper orderRecordsMapper;
    @Autowired
    CustomerInfoMapper customerInfoMapper;

    @Override
    public Result save(CustomerRelation customerRelation) throws Exception {

        customerRelation.setCt(new Date());
        customerRelation.setUt(new Date());
        customerRelationMapper.insert(customerRelation);

        return Result.success();
    }

    @Override
    public Result getCustomerRelationRecord(String customerNo,Integer current,Integer size) throws Exception{
        PageRequest pageRequest = new PageRequest();
        pageRequest.setCurrent(current);
        pageRequest.setSize(size);

        List<CustomerRelationRecord> customerRelationRecordList = new ArrayList<>();

        EntityWrapper<CustomerRelation> customerRelationEntityWrapper = new EntityWrapper<>();
        customerRelationEntityWrapper.eq("h_customer",customerNo);
        List<CustomerRelation> customerRelations = customerRelationMapper.selectList(customerRelationEntityWrapper);
        for(CustomerRelation customerRelation:customerRelations){
            CustomerRelationRecord customerRelationRecord = new CustomerRelationRecord();

            EntityWrapper<CustomerInfo> customerInfoEntityWrapper = new EntityWrapper<>();
            customerInfoEntityWrapper.eq("customer_no",customerRelation.getlCustomer());
            CustomerInfo customerInfo = customerInfoMapper.selectList(customerInfoEntityWrapper).get(0);
            customerRelationRecord.setName(customerInfo.getName());
            customerRelationRecord.setLCustomer(customerRelation.getlCustomer());

            EntityWrapper<OrderRecords> orderRecordsEntityWrapper = new EntityWrapper<>();
            orderRecordsEntityWrapper.ne("state",GoodsStatus.order);
            orderRecordsEntityWrapper.ne("state",GoodsStatus.dis);
            orderRecordsEntityWrapper.eq("openid",customerInfo.getOpenId());
            int count = orderRecordsMapper.selectCount(orderRecordsEntityWrapper);
            customerRelationRecord.setPayCount(count);
            customerRelationRecordList.add(customerRelationRecord);
        }



        pageRequest.setTotal(customerRelationRecordList.size());
        List list = new ArrayList();
        int totalRecord = customerRelationRecordList.size();// 一共多少条记录
        if(totalRecord >0){
            int firstIndex = (current - 1) * size;
            if(firstIndex<totalRecord){
                int lastIndex = current * size;
                if(lastIndex > totalRecord){
                    lastIndex = totalRecord;
                }
                 list = customerRelationRecordList.subList(firstIndex,lastIndex);
            }

        }
        pageRequest.setRecords(list);
        return Result.success(pageRequest);
    }
}
