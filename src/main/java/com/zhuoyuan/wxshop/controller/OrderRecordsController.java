package com.zhuoyuan.wxshop.controller;


import com.zhuoyuan.wxshop.dto.OrderRequest;
import com.zhuoyuan.wxshop.model.OrderRecords;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.ResponseCode;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IOrderRecordsService;
import com.zhuoyuan.wxshop.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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


    /**
     * 保存订单  线下支付
     * @param orderRequest
     * @return
     */
    @PostMapping(value = "/order/orderRecords/offline")
    public Result offline(@RequestBody OrderRequest orderRequest){
        try{
            return orderRecordsService.save(orderRequest);
        }catch (Exception e){
            log.error("OrderRecordsController-saveOrder:"+e.getMessage());
            e.printStackTrace();
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    /**
     * 线程池下订单 ，增加分布式锁
     */

    @PostMapping(value = "/order/orderRecords/offline/pool")
    public Result offlinePool(@RequestBody OrderRequest orderRequest){
        try{

             orderRecordsService.offlinePool(orderRequest);
             return Result.success(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("OrderRecordsController-saveOrder:"+e.getMessage());
            e.printStackTrace();
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }


    /**
     * 获取订单
     * @param current
     * @param size
     * @param openid
     * @param state
     * @return
     */
    @GetMapping(value = "/order/orderRecords")
    public Result getOrder(@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size,String openid,int state){
        try{
            return orderRecordsService.getOrder(current,size,openid,state);
        }catch (Exception e){
            log.error("OrderRecordsController-getOrder:"+e.getMessage());
            e.printStackTrace();
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    @PutMapping(value = "/order/orderRecords")
    public Result updateOrder(@RequestBody OrderRecords orderRecords){
        try{
            return orderRecordsService.updateOrder(orderRecords);
        }catch (Exception e){
            log.error("OrderRecordsController-updateOrder:"+e.getMessage());
            e.printStackTrace();
            return Result.failure(ResponseCode.ERROR_500,e.getMessage());
        }
    }

    //邮件更改状态
    @GetMapping(value = "/order/updateOrderByMail")
    public void updateOrderByMail(Long orderId){
        try{
             orderRecordsService.updateOrderByMail(orderId);
        }catch (Exception e){
            log.error("OrderRecordsController-updateOrderByMail:"+e.getMessage());
            e.printStackTrace();
        }
    }
}

