package com.zhuoyuan.wxshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.WXService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        return   Result.success(wxService.login(code));
    }

    public static void main(String[] args) {
         final Map<String,String> map = new HashMap<String,String>();
         Map<String,String> map2 = new HashMap<String,String>();
        map.put("1","1");
        map2.put("2","2");



        System.out.println();

    }
}
