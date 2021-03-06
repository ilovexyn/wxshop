package com.zhuoyuan.wxshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.mapper.UserInfoMapper;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.service.WXService;
import com.zhuoyuan.wxshop.status.CustomerInfoState;
import com.zhuoyuan.wxshop.status.WxInfo;
import com.zhuoyuan.wxshop.utils.BusinessIdUtil;
import com.zhuoyuan.wxshop.utils.HttpClientUtils;
import com.zhuoyuan.wxshop.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    WXService wxService;

    @Override
    public Result getWxuser(String code) {
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
        EntityWrapper<UserInfo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("open_id", jsonObject.getString("openid"));
        UserInfo userInfos = new UserInfo();
        if(userInfoMapper.selectList(entityWrapper).size() >0){
            userInfos = userInfoMapper.selectList(entityWrapper).get(0);
        }else {
            userInfos.setOpenId(jsonObject.getString("openid"));
            userInfos.setGrade(0);
            userInfos.setSumScore(new BigDecimal(0));
            userInfos.setCustomerNo("--");
        }

        log.info("userInfos(0):"+JSONObject.toJSONString(userInfos));
        return  Result.success(userInfos);
    }

    @Override
    public Result save(UserInfo userInfo) {
        EntityWrapper<UserInfo> userInfoEntityWrapper = new EntityWrapper<>();
        userInfoEntityWrapper.eq("open_id",userInfo.getOpenId());
        List<UserInfo> userInfoList =  userInfoMapper.selectList(userInfoEntityWrapper);
        if(userInfoList.size()== 0){
            SnowFlake snowFlake = new SnowFlake(1, 9);
            String customerNo = String.valueOf(snowFlake.nextId());
            userInfo.setGrade(CustomerInfoState.fristGrade);
            userInfo.setCustomerNo(customerNo);
            userInfo.setSumScore(new BigDecimal(0));
            userInfo.setTrueScore(new BigDecimal(0));
            userInfo.setCt(new Date());
            userInfo.setUt(new Date());
            userInfoMapper.insert(userInfo);
        }else{
            userInfo.setUt(new Date());
            //userInfo.setId(userInfoList.get(0).getId());
            userInfoMapper.updateById(userInfo);
        }
        userInfo.setGrade(CustomerInfoState.fristGrade);
        return Result.success();
    }

    public  String decode(String param){
        String result= null;
        try {
            result = new String(param.getBytes("utf-8"), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
