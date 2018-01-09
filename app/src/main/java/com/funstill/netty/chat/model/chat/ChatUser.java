package com.funstill.netty.chat.model.chat;


/**
 * @author liukaiyang
 * @date 2017/12/12 15:42
 */
public class ChatUser {
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private String selfDesc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", selfDesc='" + selfDesc + '\'' +
                '}';
    }
}
