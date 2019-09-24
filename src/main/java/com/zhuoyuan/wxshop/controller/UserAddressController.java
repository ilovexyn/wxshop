package com.zhuoyuan.wxshop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.dto.AddressDto;
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
@RequestMapping(value = "/api/wxserve")
public class UserAddressController {

    @Autowired
    IUserAddressService userAddressService;

    @PostMapping(value = "/userAddress")
    public Result saveUserAddress(@RequestBody UserAddress userAddress){
        try{
            userAddress.setAddressInfo(userAddress.getCity()+ userAddress.getAddressDetail());
            userAddressService.saveAddress(userAddress);
            return Result.success();
        }catch (Exception e){
            return Result.failure(ResponseCode.ERROR_500,"保存失败");
        }
    }

    @GetMapping(value = "/userAddress/{openid}")
    public Result getList(@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size,@PathVariable("openid") String openid,String state){
        EntityWrapper<UserAddress> userAddressEntityWrapper = new EntityWrapper<>();
        userAddressEntityWrapper.eq("openid",openid);
        if(null != state){
            userAddressEntityWrapper.eq("state",state);
            return  Result.success(userAddressService.selectOne(userAddressEntityWrapper));
        }
        Page page = new Page<Goods>(current, size);
        return  Result.success( userAddressService.selectPage(page,userAddressEntityWrapper));
    }

    @GetMapping(value = "/userAddress/one")
    public Result getList( @RequestParam(value ="state") int state,@RequestParam(value ="openid") String openid){
        EntityWrapper<UserAddress> userAddressEntityWrapper = new EntityWrapper<>();
        userAddressEntityWrapper.eq("openid",openid);
        userAddressEntityWrapper.eq("state",state);
        return  Result.success( userAddressService.selectOne(userAddressEntityWrapper));
    }

    @PutMapping(value = "/userAddress")
    public Result getList(@RequestBody UserAddress userAddress){
        return userAddressService.updateState(userAddress);
    }

    @GetMapping(value = "/userAddressById/{id}")
    public Result getById(@PathVariable("id") Long id){
        AddressDto addressDto = userAddressService.selectAddressById(id);
        return Result.success(addressDto);

    }
}

