package com.zhuoyuan.wxshop.utils;

import org.apache.commons.mail.SimpleEmail;

import java.util.Date;

/**
 * @program: wxshop
 * @description: ${description}
 * @author: Mr.Wang
 * @create: 2019-09-18 11:31
 **/
public class CommonEmailUtils {

    /**
     * 发送文本邮件
     *
     * @throws Exception
     */
    public static boolean sendTextMail(String strMail, String strTitle, String strText) throws Exception{
        boolean bret = false;
        SimpleEmail mail = new SimpleEmail();
        // 设置邮箱服务器信息
        mail.setSslSmtpPort("25");
        mail.setHostName("smtp.163.com");
        // 设置密码验证器
        mail.setAuthentication("13718478366@163.com", "w5211314");
        // 设置邮件发送者
        mail.setFrom("13718478366@163.com");
        // 设置邮件接收者
        mail.addTo(strMail);
        // 设置邮件编码
        mail.setCharset("UTF-8");
        // 设置邮件主题
        mail.setSubject(strTitle);
        // 设置邮件内容
        mail.setMsg(strText);
        // 设置邮件发送时间
        mail.setSentDate(new Date());
        // 发送邮件
        mail.send();
        return bret;
    }
    public static void main(String[] args) {
        try {
            if (sendTextMail("743282671@qq.com", "测试QQ邮箱发送", "你们好吗???")){
                System.out.println("QQ邮件发送成功");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
