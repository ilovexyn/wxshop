package com.zhuoyuan.wxshop.test;


import com.zhuoyuan.wxshop.mapper.OrderRecordsMapper;
import com.zhuoyuan.wxshop.model.OrderRecords;
import com.zhuoyuan.wxshop.service.IWXPayService;
import com.zhuoyuan.wxshop.service.MailService;
import com.zhuoyuan.wxshop.utils.ossService.OssUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class mail {

    @Autowired
    MailService mailService;
    @Autowired
    OrderRecordsMapper orderRecordsMapper;
    @Autowired
    IWXPayService iwxPayService;
    @Autowired
    OssUtil ossUtil;

    @Test
    public void sendMail(){
        mailService.sendHtmlMail("13718478366@163.com", "feign  撤销操作时--调用接口--发生异常", "");
    }

    @Test
    public void aVoid(){
        Integer count = orderRecordsMapper.selectTest();
        System.out.println("111");

    }


    @Test
    public void oss(){
        String key = "goods/1/下载.jpg";
        URL url = ossUtil.getURL(key);
        String result = url.toString().replace("http","https");
        System.out.println("111:"+result);
    }

    @Test
    public void ossList(){
        String key = "goods/1/details/";
        List<String> stringList = ossUtil.getUrlList(key);
        System.out.println("111:");
    }

}
