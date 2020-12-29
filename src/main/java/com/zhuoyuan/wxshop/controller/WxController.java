package com.zhuoyuan.wxshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.WXService;
import com.zhuoyuan.wxshop.utils.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: wxshop
 * @description: ${description}
 * @author: Mr.Wang
 * @create: 2019-09-18 10:08
 **/
@RestController
@Slf4j
public class WxController {

    @Autowired
    WXService wxService;

    @PostMapping(value = "/pay")
    public void pay(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        try{
            log.info("pay -- in");
            String openId =  jsonObject.getString("openId");
            String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
            String requestUri = request.getRequestURI();//得到请求的资源
            String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
            String remoteAddr = request.getRemoteAddr();//得到来访者的IP地址
            String remoteHost = request.getRemoteHost();
            int remotePort = request.getRemotePort();
            String remoteUser = request.getRemoteUser();
            String method = request.getMethod();//得到请求URL地址时使用的方法
            String pathInfo = request.getPathInfo();
            String localAddr = request.getLocalAddr();//获取WEB服务器的IP地址
            String localName = request.getLocalName();//获取WEB服务器的主机名

            log.info("1111");
            wxService.unifiedorder(openId,request);
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/getWxuser")
    public Result getWxuser(@RequestParam String code)
    {

        log.info("-- getWxuser --");
        return   Result.success(wxService.login(code));
    }

//    @GetMapping(value = "/getPic")
//    public String getWxuser(){
//        return  PictureUtils.GetImageStr("D:\\2.png");
//    }
//
//    @GetMapping(value = "/getPic2")
//    public String getWxuser(){
//        return  PictureUtils.GenerateImage("D:\\2.png");
//    }

    public static void a (){
        String a = new String("ab"); // a 为一个引用
        String b = new String("ab"); // b为另一个引用,对象的内容一样
        String aa = "ab"; // 放在常量池中
        String bb = "ab"; // 从常量池中查找

            System.out.println(aa==bb);
            System.out.println(a==b);
            System.out.println(a.equals(b));
            System.out.println(42 == 42.0);


    }


    public static void main(String[] args) {
        WxController.a();
    }
}
