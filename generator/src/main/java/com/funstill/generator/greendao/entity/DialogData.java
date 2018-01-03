package com.funstill.generator.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;

/**
 * Created by liukaiyang on 2018/1/2.
 */

@Entity
public class DialogData {
    @Id
    private Long id;
    @Unique
    private Long lastMsgId;

    private String users;
    
    private Integer unreadCount;

    private String dialogName;

    private Date createDate;
    private Date updateDate;

    @Generated(hash = 431290312)
    public DialogData(Long id, Long lastMsgId, String users, Integer unreadCount, String dialogName,
            Date createDate, Date updateDate) {
        this.id = id;
        this.lastMsgId = lastMsgId;
        this.users = users;
        this.unreadCount = unreadCount;
        this.dialogName = dialogName;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    @Generated(hash = 1372135730)
    public DialogData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastMsgId() {
        return this.lastMsgId;
    }

    public void setLastMsgId(Long lastMsgId) {
        this.lastMsgId = lastMsgId;
    }

    public Integer getUnreadCount() {
        return this.unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }


    public String getUsers() {
        return this.users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDialogName() {
        return this.dialogName;
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }


}
