package com.zhuoyuan.wxshop.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.CustomerInfo;
import com.zhuoyuan.wxshop.request.ApplyVipResult;
import com.zhuoyuan.wxshop.request.ResponseCode;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICustomerInfoService;
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
 * @since 2019-09-06
 */
@RestController
@Slf4j
public class CustomerInfoController {

    @Autowired
    ICustomerInfoService customerInfoService;

    /**
     * 保存客户自定义信息
     * @param customerInfo
     * @return
     */
    @PostMapping(value ="/customerInfo")
    public Result saveCustomerInfo (@RequestBody CustomerInfo customerInfo){
        try{
            log.info("saveCustomerInfo == begin —>"+JSONObject.toJSONString(customerInfo));
            return customerInfoService.save(customerInfo);
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    /**
     * 获取客户自定义信息
     * @param openId
     * @return
     */
    @GetMapping(value ="/customerInfo")
    public Result getCustomerInfo (String openId){
        try{
            log.info("saveCustomerInfo == begin —>openId:"+openId);
            EntityWrapper<CustomerInfo> customerInfoEntityWrapper = new EntityWrapper<>();
            customerInfoEntityWrapper.eq("open_id",openId);
            return Result.success(customerInfoService.selectOne(customerInfoEntityWrapper));
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    /**
     * 会员申请
     * @param customerInfo
     * @return
     */
    @PostMapping(value ="/applyVip" )
    public Result applyVip(@RequestBody CustomerInfo customerInfo){
        try{
            log.info("applyVip == begin —>"+JSONObject.toJSONString(customerInfo));
            return customerInfoService.applyVip(customerInfo);
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }


    @GetMapping(value ="/applyVipResult" )
    public Result applyVipResult(String openId,Integer state){
        try{
            log.info("applyVip == begin —>openId："+openId+",state:"+state);
            return customerInfoService.applyVipResult(openId,state);
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }
}

