package com.zhuoyuan.wxshop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.CustomerRelation;
import com.zhuoyuan.wxshop.request.ResponseCode;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICustomerRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-09
 */
@RestController
@RequestMapping(value = "/api/wxserve")
@Slf4j
public class CustomerRelationController {

    @Autowired
    ICustomerRelationService customerRelationService;

    @PostMapping(value ="/customerRelation")
    public Result saveCustomerRelation (@RequestBody CustomerRelation customerRelation){
        try{
            return customerRelationService.save(customerRelation);
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    @GetMapping(value ="/customerRelation")
    public Result getCustomerRelation (String lCustomer){
        try{
            EntityWrapper<CustomerRelation> customerRelationEntityWrapper = new EntityWrapper<>();
            customerRelationEntityWrapper.eq("l_customer",lCustomer);
            return Result.success(customerRelationService.selectOne(customerRelationEntityWrapper));
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }


    /**
     * 查询相关的下级人员
     */

    @GetMapping(value ="/customerRelationRecord")
    public Result getCustomerRelationRecord (@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size,String openId,Integer grade){
        try{
            return customerRelationService.getCustomerRelationRecord(openId,grade,current,size);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }
}

