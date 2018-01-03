package com.funstill.generator.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by liukaiyang on 2018/1/2.
 */
@Entity
public class MessageData {
    @Id(autoincrement = true)
    private  Long id;
    @Index(unique = true)
    private String uuid;
    @NotNull
    private Long senderId;
    private String content;
    private Integer msgType;
    @NotNull
    private Long dialogId;
    private Date createDate;
    private Date updateDate;
    @Generated(hash = 428758804)
    public MessageData(Long id, String uuid, @NotNull Long senderId, String content,
            Integer msgType, @NotNull Long dialogId, Date createDate,
            Date updateDate) {
        this.id = id;
        this.uuid = uuid;
        this.senderId = senderId;
        this.content = content;
        this.msgType = msgType;
        this.dialogId = dialogId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    @Generated(hash = 723026249)
    public MessageData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
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
}
