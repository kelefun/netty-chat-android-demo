package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.funstill.generator.greendao.dao.DaoSession;
import com.funstill.generator.greendao.dao.MessageDataDao;
import com.funstill.generator.greendao.dao.UserDataDao;
import com.funstill.generator.greendao.entity.MessageData;
import com.funstill.generator.greendao.entity.UserData;
import com.funstill.netty.chat.R;
import com.funstill.netty.chat.config.Const;
import com.funstill.netty.chat.fixtures.MessagesFixtures;
import com.funstill.netty.chat.model.User;
import com.funstill.netty.chat.model.chat.ChatMessage;
import com.funstill.netty.chat.model.chat.ChatUser;
import com.funstill.netty.chat.model.enums.MsgTypeEnum;
import com.funstill.netty.chat.model.enums.ProtoTypeEnum;
import com.funstill.netty.chat.netty.NettyClientHandler;
import com.funstill.netty.chat.observer.ProtoMsgObserver;
import com.funstill.netty.chat.protobuf.CommonMsg;
import com.funstill.netty.chat.protobuf.ProtoMsg;
import com.funstill.netty.chat.utils.AppUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.channel.Channel;

public class DefaultMessagesActivity extends BaseMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener {
    private SharedPreferences sp;
    private static Long dialogId_;
    public  ChatUser chatUser;
    public static void open(Context context, Long dialogId) {
        dialogId_ = dialogId;
        context.startActivity(new Intent(context, DefaultMessagesActivity.class));
    }

    private ProtoMsgObserver commonMsgObserver = null;
    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_messages);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();
        initObserver();
        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
    }

    private void initObserver() {
        if (commonMsgObserver == null) {
            commonMsgObserver = new ProtoMsgObserver() {
                @Override
                public void handleProtoMsg(Channel channel, ProtoMsg.Content msg) {
                    if (msg.getProtoType() == ProtoTypeEnum.COMMON_MSG.getIndex()) {
                        try {
                            CommonMsg.Content res = CommonMsg.Content.parseFrom(msg.getContent());
                            if (res.getMsgType() == MsgTypeEnum.TEXT.getIndex()) {
                                //
                                User user = getUser(res.getSender());
                                ChatMessage chatKitMsg = new ChatMessage(msg.getUuid(), user, res.getContent(), new Date());
                                messagesAdapter.addToStart(chatKitMsg, true);
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
        NettyClientHandler.msgObservable.addObserver(commonMsgObserver);
    }

    private User getUser(Long userId) {
        //先从本地数据库查
        DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
        UserDataDao userDataDao = daoSession.getUserDataDao();
        UserData userData= userDataDao.queryBuilder()
                .where(UserDataDao.Properties.UserId.eq(userId)).build().unique();
        //查不到再从服务器查

        if(userData==null){

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("userId", DefaultMessagesActivity.senderId);
            parameters.put("friendId", userId);

            //TODO 同步请求
        }
        //将查询结果保存到本机

        //返回查询结果
        User user = new User(userId + "", userData.getNickname(), userData.getAvatar(), true);
        return user;
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        //查询当前登录用户信息
        User user = new User();
        user.setAvatar(sp.getString(Const.LOGIN_AVATAR, ""));
        user.setName(sp.getString(Const.LOGIN_NICKNAME, ""));
        user.setId(sp.getString(Const.LOGIN_USER_ID, ""));
        ChatMessage msg = new ChatMessage("", user, input.toString(), new Date());
        //将自己发送的消息展示到对话框
        super.messagesAdapter.addToStart(msg, true);
        //发送消息
//        NettyClientHandler.sendMsg(input.toString());
        return true;
    }

    @Override
    public void onAddAttachments() {
        super.messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
    }

    private void initAdapter() {
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<ChatMessage>() {
                    @Override
                    public void onMessageViewClick(View view, ChatMessage message) {
                        AppUtils.showToast(DefaultMessagesActivity.this,
                                message.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if (dialogId_ != null) {
            DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
            MessageDataDao messageDataDao = daoSession.getMessageDataDao();
            List<MessageData> messageDataList = messageDataDao.queryBuilder()
                    .where(MessageDataDao.Properties.DialogId.eq(dialogId_))
                    .orderDesc(MessageDataDao.Properties.UpdateDate).list();

            ArrayList<ChatMessage> messages = new ArrayList<>(messageDataList.size());
//TODO 转换
            messagesAdapter.addToEnd(messages, false);
        }
    }
}
