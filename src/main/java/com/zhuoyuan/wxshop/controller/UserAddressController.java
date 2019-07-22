package com.zhuoyuan.wxshop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.model.Goods;
import com.zhuoyuan.wxshop.model.UserAddress;
import com.zhuoyuan.wxshop.request.ResponseCode;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author Wangjie
 * @since 2019-07-08
 */
@RestController
public class UserAddressController {

    @Autowired
    IUserAddressService userAddressService;

    @PostMapping(value = "/userAddress")
    public Result saveUserAddress(@RequestBody UserAddress userAddress){
        boolean flag = userAddressService.insert(userAddress);
        if(flag){
            return Result.success();
        }else{
            return Result.failure(ResponseCode.ERROR_500,"保存失败");
        }
    }

    @GetMapping(value = "/userAddress")
    public Result getList(@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size,@PathVariable("id") Long id){
        EntityWrapper<UserAddress> userAddressEntityWrapper = new EntityWrapper<>();
        Page page = new Page<Goods>(current, size);
        return  Result.success( userAddressService.selectPage(page,userAddressEntityWrapper));
    }
}

