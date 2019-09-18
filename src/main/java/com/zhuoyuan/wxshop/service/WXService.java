package com.zhuoyuan.wxshop.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: wxshop
 * @description: ${description}
 * @author: Mr.Wang
 * @create: 2019-09-16 11:21
 **/
public interface WXService {

    /**
     * 获取客户授权登录小程序信息
     * @return
     */
    JSONObject login(String code);

    /**
     * //微信支付
     * @param openid
     * @param request
     * @throws Exception
     */
    void unifiedorder (String openid, HttpServletRequest request) throws Exception;

}
