package com.funstill.netty.chat.model.enums;

/**
 * @author liukaiyang
 * @date 2017/12/6 15:58
 */
public enum ResponseEnum {
    SUCCESS(0, "成功"),
    //登录响应 10-
    USER_NOT_EXIST(101, "用户不存在"),
    ;
    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
