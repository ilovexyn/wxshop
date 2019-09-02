package com.zhuoyuan.wxshop.test;


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

    @Test
    public void sendMail(){
        mailService.sendHtmlMail("13718478366@163.com", "feign  撤销操作时--调用接口--发生异常", "");
    }
}
