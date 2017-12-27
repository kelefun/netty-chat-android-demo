package com.funstill.netty.chat.model.chat;

/**
 * Created by liukaiyang on 2017/12/27.
 */
public class ChatFriend {

    //TODO 测试
    public ChatFriend(String desc) {
        this.desc = desc;
    }

    private String nickname;

    private String desc;
    private String avatar;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
