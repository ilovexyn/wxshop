package com.zhuoyuan.wxshop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.OtherCustomer;
import com.zhuoyuan.wxshop.service.IOtherCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author Wangjie
 * @since 2020-01-04
 */
@RestController
public class OtherCustomerController {

    @Autowired
    IOtherCustomerService otherCustomerService;

    @GetMapping(value = "/otherCustomer/list")
    public List<OtherCustomer> getOtherCustomer(String name,String address,String mobile,String grade,String relation){
        List<OtherCustomer> otherCustomerListFinal = new ArrayList<>();
        //查询本身
        EntityWrapper<OtherCustomer> otherCustomerEntityWrapper = new EntityWrapper<>();
        if(name != null){
            otherCustomerEntityWrapper.eq("name",name);
        }
        if(address != null){
            otherCustomerEntityWrapper.eq("address",address);
        }
        if(mobile != null){
            otherCustomerEntityWrapper.eq("mobile",mobile);
        }
        if(grade != null){
            otherCustomerEntityWrapper.eq("grade",grade);
        }
        if(relation != null){
            otherCustomerEntityWrapper.eq("relation",relation);
        }
        otherCustomerService.selectList(otherCustomerEntityWrapper);
        List<OtherCustomer> otherCustomerListSelf = otherCustomerService.selectList(otherCustomerEntityWrapper);

        for(OtherCustomer otherCustomer:otherCustomerListSelf){
            otherCustomerListFinal.add(otherCustomer);
            //查询上级
            EntityWrapper<OtherCustomer> otherCustomerEntityWrapper1 = new EntityWrapper<>();
            otherCustomerEntityWrapper1.eq("name",otherCustomer.getRelation());
            List<OtherCustomer> otherCustomerListUp = otherCustomerService.selectList(otherCustomerEntityWrapper1);
            for(OtherCustomer otherCustomer1:otherCustomerListUp){
                otherCustomerListFinal.add(otherCustomer1);
            }
            //查询下级
            EntityWrapper<OtherCustomer> otherCustomerEntityWrapper2 = new EntityWrapper<>();
            otherCustomerEntityWrapper2.eq("relation",otherCustomer.getName());
            List<OtherCustomer> otherCustomerListDown =
                    otherCustomerService.selectList(otherCustomerEntityWrapper2);
            for(OtherCustomer otherCustomer2:otherCustomerListDown){
                otherCustomerListFinal.add(otherCustomer2);
            }
        }

        return otherCustomerListFinal;
    }
}

