package com.zhuoyuan.wxshop.controller;


import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
}

