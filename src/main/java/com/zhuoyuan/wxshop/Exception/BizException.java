package com.zhuoyuan.wxshop.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: wxshop
 * @description: ${description}
 * @author: Mr.Wang
 * @create: 2019-10-22 10:11
 **/
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class BizException extends  RuntimeException {
    /**
     * 错误码
     */
    protected Integer errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;
}
