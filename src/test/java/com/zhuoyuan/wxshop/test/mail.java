package com.zhuoyuan.wxshop.test;


import com.zhuoyuan.wxshop.mapper.OrderRecordsMapper;
import com.zhuoyuan.wxshop.model.OrderRecords;
import com.zhuoyuan.wxshop.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class mail {

    @Autowired
    MailService mailService;
    @Autowired
    OrderRecordsMapper orderRecordsMapper;

    @Test
    public void sendMail(){
        mailService.sendHtmlMail("13718478366@163.com", "feign  撤销操作时--调用接口--发生异常", "");
    }

    @Test
    public void aVoid(){
        Integer count = orderRecordsMapper.selectTest();
        System.out.println("111");

    }
}
