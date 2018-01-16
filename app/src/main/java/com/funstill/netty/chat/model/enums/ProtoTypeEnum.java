package com.funstill.netty.chat.model.enums;

/**
 * @author liukaiyang
 * @date 2017/12/6 15:58
 */
public enum ProtoTypeEnum {
    COMMON_MSG(1, "消息"),
    COMMON_MSG_ECHO(2,"消息回传"),
    LOGIN_REQUEST_MSG(3, "登录请求"),
    LOGIN_RESPONSE_MSG(4, "登录响应"),
    LOGOUT_REQUEST_MSG(5, "注销"),
    HEART_BEAT_PING(6, "心跳ping包"),
    ONLINE_REQUEST_MSG(7,"上线请求");
    private int index;
    private String name;

    ProtoTypeEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
