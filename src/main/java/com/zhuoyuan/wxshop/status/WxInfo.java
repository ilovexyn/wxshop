package com.zhuoyuan.wxshop.status;

public class WxInfo {

    public final static String appId = "wx12fee77b24ac4a51";

    public final static String secret = "431be374f821923354e50b53bfeda46b";

    public final static String jscode2sessionUrl = "https://api.weixin.qq.com/sns/jscode2session";//获取微信用户信息

    // =============== 微信支付   统一下单
    public static final String mch_id = "1543160831"; //微信支付的商户id
    public static final String key = "";  //微信支付的商户密钥
    public static final String notify_url = "https://??/??/weixin/api/wxNotify";   //支付成功后的服务器回调url
    public static final String SIGNTYPE = "MD5"; //签名方式，固定值
    public static final String TRADETYPE = "JSAPI";  //交易类型，小程序支付的固定值为JSAPI
    public static final String   unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单



}
