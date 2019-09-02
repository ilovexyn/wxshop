package com.zhuoyuan.wxshop.service;

import java.net.URL;
import java.util.List;

/**
 * @ClassName MailService
 * @Description 邮件发送
 * @Author houQiang
 * @Date 2018年6月29日11:31:36
 * @Version 1.0
**/
public interface MailService {
    public void sendSimpleMail(String to, String subject, String content);
    public Boolean sendHtmlMail(String to, String subject, String content);
    public Boolean sendAttachmentsMail(String to, String subject, String content, String cc);
    public Boolean sendAttachmentsMail(String to, String subject, String content, String cc, String cc2);
    public Boolean sendAttachmentsMail(String to, String subject, String content, String cc, String cc2, String cc3);
    public Boolean sendAttachmentsMail(String to, String subject, String content, String cc, String cc2, String cc3, String cc4, String cc5, String cc6);


    /*
       to ，收件人
       subject，主题
       content 内容
       fileName 附件名字
       url 附件路径
       cc 抄送人
     */
    public Boolean sendAttachmentsMail(String to, String subject, String content, String fileName, URL url, String cc);
    public Boolean sendAttachmentsMail(String to, String subject, String content, String fileName, URL url, String cc, String cc2);
    public Boolean sendAttachmentsMail(String to, String subject, String content, String fileName, URL url, String cc1, String cc2, String cc3, String cc4);
    public Boolean sendAttachmentsMail(String to, String subject, String content, String fileName, URL url);

    public Boolean sendMail(String to, String subject, String content, String fileName, URL url, List list);

}
