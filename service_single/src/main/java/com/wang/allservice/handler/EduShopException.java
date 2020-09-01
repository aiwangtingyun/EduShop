package com.wang.allservice.handler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor      // 生成无参数构造
@AllArgsConstructor     // 生成有参数构造
public class EduShopException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;   // 状态码
    @ApiModelProperty(value = "异常信息")
    private String msg;     // 异常信息

    @Override
    public String toString() {
        return "EduShopException {" +
                "message = " + this.getMsg() +
                ", code = " + code +
                "}";
    }
}
