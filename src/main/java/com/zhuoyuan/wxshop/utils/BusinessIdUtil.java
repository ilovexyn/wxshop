package com.zhuoyuan.wxshop.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


/**
 * 按照业务规则生成id
 * wangjie
 * --YW_20151218_emp34_93181
 */
@Service
public class BusinessIdUtil {
    private static final Logger logger = LoggerFactory.getLogger(BusinessIdUtil.class);

      //生成增人id
      public static String CreateBusinessId(String tpa,String type){
          try{
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
              String sdate = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
              String BusinessId = "";
              StringBuffer bisnussId = new StringBuffer(tpa+"_");
              bisnussId.append(type+"_");
              bisnussId.append(sdate+"_");
              bisnussId.append((int) (Math.random() * 100000));
              BusinessId = bisnussId.toString();
              return BusinessId;
          }catch (Exception e){
              logger.error("----BusinessIdUtil 异常----"+e);
              return null;
          }
      }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     * @author
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}




