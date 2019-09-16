package com.zhuoyuan.wxshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.mapper.UserInfoMapper;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.status.CustomerInfoState;
import com.zhuoyuan.wxshop.status.WxInfo;
import com.zhuoyuan.wxshop.utils.BusinessIdUtil;
import com.zhuoyuan.wxshop.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return  Result.success(jsonObject);
    }

    @Override
    public Result save(UserInfo userInfo) {
        String customerNo = BusinessIdUtil.CreateBusinessId("A","1");
        EntityWrapper<UserInfo> userInfoEntityWrapper = new EntityWrapper<>();
        userInfoEntityWrapper.eq("open_id",userInfo.getOpenId());
        userInfo.setCustomerNo(customerNo);
        List<UserInfo> userInfoList =  userInfoMapper.selectList(userInfoEntityWrapper);
        if(userInfoList.size()== 0){
            userInfo.setCt(new Date());
            userInfo.setUt(new Date());
            userInfoMapper.insert(userInfo);
        }else{
            userInfo.setUt(new Date());
            userInfo.setId(userInfoList.get(0).getId());
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
