package com.wang.allservice.utils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RetMsg {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    // 构造方法私有化
    private RetMsg() {}

    // 成功
    public static RetMsg ok() {
        RetMsg msg = new RetMsg();
        msg.setSuccess(true);
        msg.setCode(ResultCode.SUCCESS);
        msg.setMessage("成功");
        return msg;
    }

    // 失败
    public static RetMsg error() {
        RetMsg msg = new RetMsg();
        msg.setSuccess(false);
        msg.setCode(ResultCode.ERROR);
        msg.setMessage("失败");
        return msg;
    }

    // 下面方法用于对象的链式操作
    public RetMsg success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public RetMsg message(String message) {
        this.setMessage(message);
        return this;
    }

    public RetMsg code(Integer code) {
        this.setCode(code);
        return this;
    }

    public RetMsg data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public RetMsg data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
