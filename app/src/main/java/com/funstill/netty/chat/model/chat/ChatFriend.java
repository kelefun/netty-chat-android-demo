package com.funstill.netty.chat.model.chat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liukaiyang on 2017/12/27.
 */
public class ChatFriend {

    //TODO 测试
    public ChatFriend(String selfDesc) {
        this.selfDesc = selfDesc;
    }

    @SerializedName("friendNoteName")
    private String nickname;


    private String selfDesc;
    private String avatar;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSelfDesc() {
        return selfDesc;
    }

    public void setSelfDesc(String selfDesc) {
        this.selfDesc = selfDesc;
    }

    @Override
    public String toString() {
        return "ChatFriend{" +
                "nickname='" + nickname + '\'' +
                ", desc='" + selfDesc + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }


}
