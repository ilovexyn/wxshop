package com.zhuoyuan.wxshop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
@RestController
public class UserInfoController {

    @Autowired
    IUserInfoService userInfoService;

    @GetMapping(value = "/userInfo/getWxuser")
    public Result getWxuser(@RequestParam String code)
    {
        return userInfoService.getWxuser(code);
    }

    @PostMapping(value = "/userInfo/save")
    public Result saveUser(@RequestBody UserInfo userInfo){
        return userInfoService.save(userInfo);
    }

    @GetMapping(value ="/userInfo/getUserInfo")
    public Result getUserInfo(@RequestParam String openid){
        EntityWrapper<UserInfo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("open_id",openid);
        List<UserInfo> userInfoList = userInfoService.selectList(entityWrapper);
        if(userInfoList.size() > 0 ){
            return Result.success(userInfoList.get(0));
        }else{
            return null;
        }
    }
}

