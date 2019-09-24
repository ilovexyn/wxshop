package com.zhuoyuan.wxshop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.CoverImage;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICoverImageService;
import com.zhuoyuan.wxshop.utils.ossService.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
public class CoverImageController {

    @Autowired
    ICoverImageService coverImageService;
    @Autowired
    OssUtil ossUtil;


    @GetMapping(value = "/cover")
    public Result cover(){
        CoverImage coverImage = coverImageService.selectById((long)1);
        List<String> strings = ossUtil.getUrlList(coverImage.getUrl());
        strings.remove(0);
        return Result.success(strings);
    }
}

