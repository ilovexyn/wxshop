package com.zhuoyuan.wxshop.service.impl;

import com.zhuoyuan.wxshop.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.List;



@Service
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    static {
        System.setProperty("mail.mime.splitlongparameters", "false");
    }


    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage(); //建立邮件消息
            message.setFrom(from);       // 发送者
            message.setTo(to);           // 接收者
            message.setSubject(subject); // 邮件主题.
            message.setText(content);    // 发送的内容
            mailSender.send(message);
            logger.info("简单邮件发送成功！");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！" + e);
        }
    }


    @Override
    public Boolean sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);               // 发送者
            helper.setTo(to);                   // 接收者
            helper.setSubject(subject);         // 邮件主题.
            helper.setText(content, true);// 发送的内容
            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！" + e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean sendAttachmentsMail(String to, String subject, String content, String cc) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                    // 接收者
            helper.setSubject(subject);          // 邮件主题.
            helper.setText(content, true); // 发送的内容
            helper.setCc(cc);                        //  转发
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean sendAttachmentsMail(String to, String subject, String content, String cc,String cc2) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String all_cc []={cc,cc2};
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                    // 接收者
            helper.setSubject(subject);          // 邮件主题.
            helper.setText(content, true); // 发送的内容
            helper.setCc(all_cc);                        //  转发
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean  sendAttachmentsMail(String to, String subject, String content, String fileName, URL url,String cc) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                        // 接收者
            helper.setCc(cc);                        //  转发
            helper.setSubject(subject);       //  邮件主题
            helper.setText(content, true); // 发送的内容
            DataSource dataSource = new URLDataSource(url);
            helper.addAttachment(fileName, dataSource); // 附件
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return  false;
        }
        return true;
    }

    @Override
    public Boolean  sendAttachmentsMail(String to, String subject, String content, String fileName, URL url,String cc,String cc2) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String all_cc []={cc,cc2};
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                        // 接收者
            helper.setCc(all_cc);                        //  转发
            helper.setSubject(subject);       //  邮件主题
            helper.setText(content, true); // 发送的内容
            DataSource dataSource = new URLDataSource(url);
            helper.addAttachment(fileName, dataSource); // 附件
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return  false;
        }
        return true;
    }

    @Override
    public Boolean  sendAttachmentsMail(String to, String subject, String content,String cc,String cc2,String cc3) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String all_cc []={cc,cc2,cc3};
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                    // 接收者
            helper.setSubject(subject);          // 邮件主题.
            helper.setText(content, true); // 发送的内容
            helper.setCc(all_cc);                        //  转发
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return  false;
        }
        return true;
    }

    @Override
    public Boolean  sendAttachmentsMail(String to, String subject, String content,String cc,String cc2,String cc3,String cc4,String cc5,String cc6) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String all_cc []={cc,cc2,cc3,cc4,cc5,cc6};
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                    // 接收者
            helper.setSubject(subject);          // 邮件主题.
            helper.setText(content, true); // 发送的内容
            helper.setCc(all_cc);                        //  转发
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return  false;
        }
        return true;
    }

    @Override
    public Boolean  sendAttachmentsMail(String to, String subject, String content, String fileName, URL url) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                        // 接收者
            helper.setSubject(subject);       //  邮件主题
            helper.setText(content, true); // 发送的内容
            DataSource dataSource = new URLDataSource(url);
            helper.addAttachment(fileName, dataSource); // 附件
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return  false;
        }
        return true;
    }

    @Override
    public Boolean  sendAttachmentsMail(String to, String subject, String content, String fileName, URL url,String cc1,String cc2,String cc3,String cc4) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String all_cc []={cc1,cc2,cc3,cc4};
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                        // 接收者
            helper.setCc(all_cc);                   //抄送
            helper.setSubject(subject);       //  邮件主题
            helper.setText(content, true); // 发送的内容
            DataSource dataSource = new URLDataSource(url);
            helper.addAttachment(fileName, dataSource); // 附件
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！" + e);
            return  false;
        }
        return true;
    }


    @Override
    public Boolean sendMail(String to,String subject, String content, String fileName, URL url,List list){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String [] all_cc = (String[]) list.toArray(new String[0]);
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);                // 发送者
            helper.setTo(to);                        // 接收者
            helper.setCc(all_cc);                   //抄送
            helper.setSubject(subject);       //  邮件主题
            helper.setText(content, true); // 发送的内容
            if(null != url){
                DataSource dataSource = new URLDataSource(url);
                helper.addAttachment(fileName, dataSource); // 附件
            }
            mailSender.send(message);
            logger.info("邮件已发送");
        } catch (Exception e) {
            logger.error("邮件发送异常！" + e);
            e.printStackTrace();
            return  false;
        }
        return true;

    }
}
