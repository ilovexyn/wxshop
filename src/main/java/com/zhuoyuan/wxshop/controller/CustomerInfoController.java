package com.zhuoyuan.wxshop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.CustomerInfo;
import com.zhuoyuan.wxshop.request.ApplyVipResult;
import com.zhuoyuan.wxshop.request.ResponseCode;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-06
 */
@RestController
@RequestMapping(value = "/api/wxserve")
public class CustomerInfoController {

    @Autowired
    ICustomerInfoService customerInfoService;

    @PostMapping(value ="/customerInfo")
    public Result saveCustomerInfo (@RequestBody CustomerInfo customerInfo){
        try{
            return customerInfoService.save(customerInfo);
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    @GetMapping(value ="/customerInfo")
    public Result getCustomerInfo (String openId){
        try{
            EntityWrapper<CustomerInfo> customerInfoEntityWrapper = new EntityWrapper<>();
            customerInfoEntityWrapper.eq("open_id",openId);
            return Result.success(customerInfoService.selectOne(customerInfoEntityWrapper));
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    @PostMapping(value ="/applyVip" )
    public Result applyVip(@RequestBody CustomerInfo customerInfo){
        try{
            return customerInfoService.applyVip(customerInfo);
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }


    @GetMapping(value ="/applyVipResult" )
    public Result applyVipResult(String openId,Integer state){
        try{
            return customerInfoService.applyVipResult(openId,state);
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }
}

