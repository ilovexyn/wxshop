package com.zhuoyuan.wxshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.CustomerInfo;
import com.zhuoyuan.wxshop.mapper.CustomerInfoMapper;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.ApplyVipResult;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.ICustomerInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.service.IUserInfoService;
import com.zhuoyuan.wxshop.service.MailService;
import com.zhuoyuan.wxshop.status.CustomerInfoState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-06
 */
@Service
@Slf4j
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

    @Autowired
    CustomerInfoMapper customerInfoMapper;
    @Autowired
    MailService mailService;
    @Autowired
    IUserInfoService userInfoService;
    @Value("${spring.mail.username}")
    private String mailUrl;

    @Override
    public Result save(CustomerInfo customerInfo)throws Exception {
        EntityWrapper<CustomerInfo> customerInfoEntityWrapper = new EntityWrapper<>();
        customerInfoEntityWrapper.eq("open_id",customerInfo.getOpenId());
        List<CustomerInfo> customerInfoList = customerInfoMapper.selectList(customerInfoEntityWrapper);
        if(customerInfoList.size()>0){
            CustomerInfo tcustomerInfo = customerInfoList.get(0);
            customerInfo.setId(tcustomerInfo.getId());
            customerInfoMapper.updateById(customerInfo);
        }else{
            customerInfo.setCt(new Date());
            customerInfo.setUt(new Date());
            customerInfoMapper.insert(customerInfo);
        }

        return Result.success();
    }

    @Override
    public Result applyVip(CustomerInfo customerInfo) throws Exception{

        EntityWrapper<CustomerInfo> customerInfoEntityWrapper = new EntityWrapper<>();
        customerInfoEntityWrapper.eq("open_Id",customerInfo.getOpenId());
        List<CustomerInfo> customerInfoList =  customerInfoMapper.selectList(customerInfoEntityWrapper);
        if(customerInfoList.size() < 1){
            return Result.failure("600","请·先完善个人信息");
        }else{
            String url1 ="https://www.1000000tao.com/api/wxserve/order/applyVipResult?state=1&openId="+customerInfo.getOpenId() ;
            String url2 ="https://www.1000000tao.com/api/wxserve/order/applyVipResult?state=0&openId="+customerInfo.getOpenId() ;

            String content = "姓名："+ customerInfoList.get(0).getName()+" 证件号码："+customerInfoList.get(0).getIdno()+"通过："+url1+
                    "不通过："+url2;
            Boolean flag =  mailService.sendHtmlMail(mailUrl, "会员申请", content);
            log.info("邮件结果："+flag);
        }
        return Result.success();
    }

    @Override
    public Result applyVipResult(String openId,Integer state) throws Exception {
        EntityWrapper<CustomerInfo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("open_id",openId);
        List<CustomerInfo> customerInfoList = customerInfoMapper.selectList(entityWrapper);
        String mail = customerInfoList.get(0).getMail();
        String content = "";
        if(state == 1){
            content = "会员申请通过";
            EntityWrapper<UserInfo> userInfoEntityWrapper = new EntityWrapper<>();
            userInfoEntityWrapper.eq("open_id",openId);
            UserInfo userInfo = userInfoService.selectOne(userInfoEntityWrapper);
            userInfo.setGrade(CustomerInfoState.fouthGrade);
            userInfoService.updateById(userInfo);
        }else if(state == 0){
            content = "会员申请失败";
        }
        Boolean flag =  mailService.sendHtmlMail(mail, "会员申请结果通知", content);
        return Result.success();
    }
}
