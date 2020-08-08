package com.wang.servicebase.exceptionhandler;

import com.wang.commonutis.ExceptionUtils;
import com.wang.commonutis.RetMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 全局异常处理
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理全局所有异常
    @ExceptionHandler(Exception.class)
    @ResponseBody   // 为了返回数据
    public RetMsg error(Exception e) {
        e.printStackTrace();
        return RetMsg.error().message("执行全局异常处理....");
    }

    // 处理特定的算术异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody   // 为了返回数据
    public RetMsg error(ArithmeticException e) {
        e.printStackTrace();
        return RetMsg.error().message("出现了算术异常....");
    }

    // 处理自定义异常
    @ExceptionHandler(EduShopException.class)
    @ResponseBody   // 为了返回数据
    public RetMsg error(EduShopException e) {
        log.error(ExceptionUtils.getMessage(e));
        e.printStackTrace();
        return RetMsg.error().code(e.getCode()).message(e.getMsg());
    }
}
