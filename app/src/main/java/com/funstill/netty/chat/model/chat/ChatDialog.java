package com.funstill.netty.chat.model.chat;

import com.funstill.netty.chat.model.BaseModel;
import com.funstill.netty.chat.model.User;
import com.stfalcon.chatkit.commons.models.IDialog;

import java.util.ArrayList;

/**
 * Created by liukaiyang on 2017/12/21.
 */

public class ChatDialog extends BaseModel implements IDialog<ChatMessage> {

    /**
     * 会话对象id(用户id,或群组id)
     */
    private String chatObjId;
    /**
     * 会话类型 1普通单聊会话,2聊天室,
     */
    private int chatType;
    /**
     * 会话图片地址
     */
    private String dialogPhoto;
    /**
     * 会话名称
     */
    private String dialogName;
    /**
     * 会话用户
     */
    private ArrayList<User> users;
    /**
     * 最近一条消息
     */
    private ChatMessage lastMessage;
    private int unreadCount;


    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public ChatMessage getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(ChatMessage message) {
        this.lastMessage = message;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setDialogPhoto(String dialogPhoto) {
        this.dialogPhoto = dialogPhoto;
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }
}
