package com.zhuoyuan.wxshop.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.mapper.GoodsMapper;
import com.zhuoyuan.wxshop.model.Goods;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IGoodsService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
@RestController
@RequestMapping(value = "/api/wxserve")
@Slf4j
public class GoodsController {

    @Autowired
    IGoodsService goodsService;
    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 商城首页列表
     * @param current
     * @param size
     * @return
     */
    @GetMapping(value = "/goods/all")
    public Result getAll(@RequestParam(value ="current",defaultValue = "1") int current, @RequestParam(value ="size",defaultValue = "10") int size,@RequestParam("name") String name){
        Page<Goods> goodsPage = goodsService.getList(current,size,name);
        return Result.success(goodsPage);
    }

    @GetMapping(value = "/goods/byId/{id}")
    public Result getById(@PathVariable("id") Long id){
        return  Result.success(goodsService.getById(id));
    }

}

