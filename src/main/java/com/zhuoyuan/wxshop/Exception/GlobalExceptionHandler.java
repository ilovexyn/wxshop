//package com.zhuoyuan.wxshop.Exception;
//
//import com.zhuoyuan.wxshop.request.ResponseCode;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.http.ResponseUtil;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @program: wxshop
// * @description: ${description}
// * @author: Mr.Wang
// * @create: 2019-10-22 10:11
// **/
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    /**
//     * 处理空指针的异常
//     * @param req
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value =NullPointerException.class)
//    public BaseResponseFacade exceptionHandler(HttpServletRequest req, NullPointerException e){
//        log.error("发生空指针异常！原因是:",e);
//        return ResponseUtil.error(ResponseCode.ERROR);
//    }
//}
