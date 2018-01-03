package com.funstill.generator.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liukaiyang on 2018/1/2.
 */

@Entity
public class UserData {
    @Index(unique = true)
    private Long userId;
    private String avatar;
    private String nickname;

    @Generated(hash = 1851988291)
    public UserData(Long userId, String avatar, String nickname) {
        this.userId = userId;
        this.avatar = avatar;
        this.nickname = nickname;
    }

    @Generated(hash = 1838565001)
    public UserData() {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
