package com.zhuoyuan.wxshop.controller;


import com.zhuoyuan.wxshop.dto.CarShopDto;
import com.zhuoyuan.wxshop.dto.CarShopOrderDto;
import com.zhuoyuan.wxshop.model.CarShop;
import com.zhuoyuan.wxshop.request.CarShopPageRequest;
import com.zhuoyuan.wxshop.request.PageRequest;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICarShopService;
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
 * @since 2019-09-25
 */
@RestController
@RequestMapping(value = "/api/wxserve")
@Slf4j
public class CarShopController {

    @Autowired
    ICarShopService carShopService;

    /**
     *  获取购物车信息
     * @param current
     * @param size
     * @param openid
     * @return
     */
    @GetMapping(value = "/carShop")
    public Result getCarShop(@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size, @RequestParam("openid") String openid){
        CarShopPageRequest carShopPageRequest = carShopService.getCarShop(current,size,openid);
        return Result.success(carShopPageRequest);
    }

    /**
     * 添加购物车
     * @param carShop
     * @return
     */
    @PostMapping(value = "/carShop")
    public Result addCarShop(@RequestBody CarShop carShop){
        try{
            carShopService.addCarShop(carShop);
            return  Result.success();
        }catch (Exception e){
            log.error("addCarShop -- Exception"+e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取购物车 订单列表
     * @param current
     * @param size
     * @param openid
     * @return
     */
    @GetMapping(value = "/carShopOrder")
    public Result getCarShopOrder(@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size, @RequestParam("openid") String openid){
        CarShopPageRequest carShopPageRequest = carShopService.carShopOrder(current,size,openid);
        return Result.success(carShopPageRequest);
    }

    /**
     * 通过购物车下单
     * @param carShop
     * @return
     */
//    @PostMapping(value = "/carShopOrder")
//    public Result getCarShop(@RequestBody CarShop carShop){
//        CarShopPageRequest carShopPageRequest = carShopService.getCarShop(current,size,openid);
//        return Result.success(carShopPageRequest);
//    }



}

