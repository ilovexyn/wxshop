package com.zhuoyuan.wxshop.controller;


import com.zhuoyuan.wxshop.dto.OrderRequest;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.ResponseCode;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IOrderRecordsService;
import com.zhuoyuan.wxshop.service.IUserInfoService;
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
 * @since 2019-06-30
 */
@RestController
@Slf4j
public class OrderRecordsController {

    @Autowired
    IOrderRecordsService orderRecordsService;

    @PostMapping(value = "/order/orderRecords")
    public Result saveOrder(@RequestBody OrderRequest orderRequest){
        try{
            return orderRecordsService.save(orderRequest);
        }catch (Exception e){
            log.error("OrderRecordsController-saveOrder:"+e.getMessage());
            e.printStackTrace();
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    @GetMapping(value = "/order/orderRecords")
    public Result getOrder(@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size,String openid,int state){
        try{
            return orderRecordsService.getOrder(current,size,openid,state);
        }catch (Exception e){
            log.error("OrderRecordsController-saveOrder:"+e.getMessage());
            e.printStackTrace();
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }
}

