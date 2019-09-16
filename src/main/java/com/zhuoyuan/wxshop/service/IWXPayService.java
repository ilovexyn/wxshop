package com.zhuoyuan.wxshop.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: wxshop
 * @description: ${description}
 * @author: Mr.Wang
 * @create: 2019-09-16 11:21
 **/
public interface IWXPayService {

    void unifiedorder (String openid, HttpServletRequest request) throws Exception;//微信支付

}
