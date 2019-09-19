package com.zhuoyuan.wxshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.WXService;
import com.zhuoyuan.wxshop.status.WxInfo;
import com.zhuoyuan.wxshop.utils.HttpClientUtils;
import com.zhuoyuan.wxshop.utils.wx.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: wxshop
 * @description: ${description}
 * @author: Mr.Wang
 * @create: 2019-09-16 11:21
 **/
@Service
@Slf4j
public class WXServiceImpl implements WXService {


    @Override
    public void unifiedorder(String openid, HttpServletRequest request) throws Exception {
        //生成的随机字符串
        String  nonce_str = PayUtil.getRandomStringByLength(32);
        //商品名称
        String body = "测试商品名称";
        //获取客户端的ip地址
        String spbill_create_ip = PayUtil.getIpAddr(request);

        //组装参数，用户生成统一下单接口的签名
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", WxInfo.appId);
        packageParams.put("mch_id", WxInfo.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", "123456789");//商户订单号
        packageParams.put("total_fee", "1");//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", WxInfo.notify_url);//支付成功后的回调地址
        packageParams.put("trade_type", WxInfo.TRADETYPE);//支付方式
        packageParams.put("openid", openid);

        String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mysign = PayUtil.sign(prestr, WxInfo.key, "utf-8").toUpperCase();

        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + WxInfo.appId + "</appid>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<mch_id>" + WxInfo.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + WxInfo.notify_url + "</notify_url>"
                + "<openid>" + openid + "</openid>"
                + "<out_trade_no>" + "123456789" + "</out_trade_no>"
                + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                + "<total_fee>" + "1" + "</total_fee>"
                + "<trade_type>" + WxInfo.TRADETYPE + "</trade_type>"
                + "<sign>" + "B72115A474E490FB72DBD0A82E3D4651" + "</sign>"
                + "</xml>";

        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

        //调用统一下单接口，并接受返回的结果
        String result = PayUtil.httpRequest(WxInfo.unifiedorderUrl, "POST", xml);

        System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

        // 将解析结果存储在HashMap中
        Map map = PayUtil.doXMLParse(result);

        String return_code = (String) map.get("return_code");//返回状态码

        Map<String, Object> response = new HashMap<String, Object>();//返回给小程序端需要的参数
        if(return_code.equals("SUCCESS")){
            String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
            response.put("nonceStr", nonce_str);
            response.put("package", "prepay_id=" + prepay_id);
            Long timeStamp = System.currentTimeMillis() / 1000;
            response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
            //拼接签名需要的参数
            String stringSignTemp = "appId=" + WxInfo.appId + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
            //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
            String paySign = PayUtil.sign(stringSignTemp, WxInfo.key, "utf-8").toUpperCase();
            response.put("paySign", paySign);
        }
        response.put("appid", WxInfo.appId);
    }

    @Override
    public JSONObject login(String code) {
        String appId = WxInfo.appId;
        String secret =WxInfo.secret;
        String js_code =code;
        String grant_type ="authorization_code";
        String url = WxInfo.jscode2sessionUrl;
        url=url+"?appid="+appId;
        url=url+"&secret="+secret;
        url=url+"&js_code="+js_code;
        url=url+"&grant_type="+grant_type;
        JSONObject jsonObject =  HttpClientUtils.httpGet(url);
        log.info("jsonObject:"+((JSONObject) jsonObject).toString());
        return  jsonObject;
    }
}
