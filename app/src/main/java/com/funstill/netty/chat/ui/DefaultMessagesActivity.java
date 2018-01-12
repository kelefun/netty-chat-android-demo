package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.funstill.generator.greendao.dao.DaoSession;
import com.funstill.generator.greendao.dao.DialogDataDao;
import com.funstill.generator.greendao.dao.MessageDataDao;
import com.funstill.generator.greendao.dao.UserDataDao;
import com.funstill.generator.greendao.entity.DialogData;
import com.funstill.generator.greendao.entity.MessageData;
import com.funstill.generator.greendao.entity.UserData;
import com.funstill.netty.chat.R;
import com.funstill.netty.chat.api.FriendApi;
import com.funstill.netty.chat.api.UserApi;
import com.funstill.netty.chat.config.Const;
import com.funstill.netty.chat.config.ServerConfig;
import com.funstill.netty.chat.fixtures.MessagesFixtures;
import com.funstill.netty.chat.model.User;
import com.funstill.netty.chat.model.chat.ChatFriend;
import com.funstill.netty.chat.model.chat.ChatMessage;
import com.funstill.netty.chat.model.chat.ChatUser;
import com.funstill.netty.chat.model.enums.DialogTypeEnum;
import com.funstill.netty.chat.model.enums.MsgTypeEnum;
import com.funstill.netty.chat.model.enums.ProtoTypeEnum;
import com.funstill.netty.chat.netty.NettyClientHandler;
import com.funstill.netty.chat.observer.ProtoMsgObserver;
import com.funstill.netty.chat.protobuf.CommonMsg;
import com.funstill.netty.chat.protobuf.ProtoMsg;
import com.funstill.netty.chat.utils.AppUtils;
import com.funstill.netty.chat.utils.RetrofitUtil;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.netty.channel.Channel;
import retrofit2.Call;

public class DefaultMessagesActivity extends BaseMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener {
    private SharedPreferences sp;
    private final String TAG = "DefaultMessagesActivity";
    private final int RECEIVE_MSG = 1;
    private static Long dialogId_, friendUserId_;

    private ProtoMsgObserver commonMsgObserver = null;
    private MessagesList messagesList;

    public static void open(Context context, Long dialogId, Long friendUserId) {
        if (friendUserId == null) {
            throw new IllegalArgumentException("好友id不能为空");//会话id可以为空(之前没聊过)
        }
        dialogId_ = dialogId;
        friendUserId_ = friendUserId;
        context.startActivity(new Intent(context, DefaultMessagesActivity.class));
    }


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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVE_MSG:{
                    messagesAdapter.addToStart((ChatMessage) msg.obj, true);
                }
            }

        }
    };

    private void initObserver() {
        if (commonMsgObserver == null) {
            commonMsgObserver = new ProtoMsgObserver() {
                @Override
                public void handleProtoMsg(Channel channel, ProtoMsg.Content msg) {
                    if (msg.getProtoType() == ProtoTypeEnum.COMMON_MSG.getIndex()) {//别人发来的消息

                        CommonMsg.Content res = saveMsg(msg, 1);
                        if (res != null) {
                            if (res.getMsgType() == MsgTypeEnum.TEXT.getIndex()) {
                                User user = getUser(res.getSender());
                                Log.d(TAG, "收到来自<" + user.getName() + ">的消息...");
                                ChatMessage chatKitMsg = new ChatMessage(msg.getUuid(), user, res.getContent(), new Date());
                                Message handlerMsg = new Message();
                                handlerMsg.obj = chatKitMsg;
                                handlerMsg.what = RECEIVE_MSG;
                                handler.sendMessage(handlerMsg);

                            }//TODO 图片
                        }
                    } else if (msg.getProtoType() == ProtoTypeEnum.COMMON_MSG_ECHO.getIndex()) {//自己发送的消息
                        //保存消息
                        Log.d(TAG, "发送消息成功,收到回传消息,准备保存数据到本机...");
                        saveMsg(msg, 2);

                    }
                }
            };
        }
        NettyClientHandler.msgObservable.addObserver(commonMsgObserver);
    }


    private CommonMsg.Content saveMsg(ProtoMsg.Content msg, int fromType) {
        try {
            CommonMsg.Content res = CommonMsg.Content.parseFrom(msg.getContent());
            DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
            DialogDataDao dialogDataDao = daoSession.getDialogDataDao();
            DialogData dialogData;
            if (dialogId_ == null) {//第一次聊天
                User friend = getUser(fromType == 1 ? res.getSender() : res.getReceiver());
                dialogData = new DialogData();
                dialogData.setDialogName(friend.getName());
                dialogData.setDialogType(DialogTypeEnum.PRIVATE_DIALOG.getIndex());
                dialogData.setUnreadCount(0);
                dialogData.setUsers(friend.getId().toString());
                long dialogId = dialogDataDao.insert(dialogData);
                dialogId_ = dialogId;
            } else {
                dialogData = dialogDataDao.queryBuilder().where(DialogDataDao.Properties.Id.eq(dialogId_)).build().unique();
            }

            MessageDataDao messageDataDao = daoSession.getMessageDataDao();
            MessageData messageData = new MessageData(null, msg.getUuid(), res.getSender(),
                    res.getReceiver(), res.getContent(), res.getMsgType(), dialogId_, new Date(), new Date());
            messageDataDao.insert(messageData);
            dialogData.setLastMsgId(messageData.getId());
            //TODO 更新会话的最近一条消息
            return res;
        } catch (Exception e) {
            Log.e(TAG, "保存聊天消息异常" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private User getUser(Long userId) {
        //先从本地数据库查
        DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
        UserDataDao userDataDao = daoSession.getUserDataDao();
        UserData userData = userDataDao.queryBuilder()
                .where(UserDataDao.Properties.UserId.eq(userId)).build().unique();
        //查不到再从服务器查
        if (userData == null) {
            userData = new UserData();
            UserApi userApi = RetrofitUtil.retrofit(ServerConfig.WEB_URL).create(UserApi.class);
            FriendApi friendApi = RetrofitUtil.retrofit(ServerConfig.WEB_URL).create(FriendApi.class);
            try {
                Call<ChatUser> call = userApi.getUser(userId);
                Call<ChatFriend> callFriend = friendApi.getFriendDetail(Long.valueOf(senderId), userId);
                ChatUser chatUser = call.execute().body();
                ChatFriend chatFriend = callFriend.execute().body();
                userData.setAvatar(chatUser.getAvatar());
                if (chatFriend != null) {
                    userData.setNickname(chatFriend.getNickname());
                }
                userData.setUserId(chatUser.getUserId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将查询结果保存到本机
            userDataDao.insert(userData);
        }
        //返回查询结果
        User user = new User(userId + "", userData.getNickname(), userData.getAvatar(), true);
        return user;
    }

    //TODO 需要改ui ,如果未加载历史消息,需要上屏到顶部
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
        CommonMsg.Content.Builder body = CommonMsg.Content.newBuilder();
        body.setMsgType(MsgTypeEnum.TEXT.getIndex());
        body.setReceiver(friendUserId_);
        body.setSender(Long.valueOf(senderId));
        body.setContent(input.toString());
        ProtoMsg.Content.Builder msgBuilder = ProtoMsg.Content.newBuilder();
        msgBuilder.setProtoType(ProtoTypeEnum.COMMON_MSG.getIndex());
        msgBuilder.setContent(body.build().toByteString());
        NettyClientHandler.channel.writeAndFlush(msgBuilder.build());
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

    //TODO 需要改ui 这是滑动到顶部加载更多..需要初始化历史数据并不只是加载更多
    @Override
    public void onLoadMore(int page, int totalItemsCount) {
//        Log.d(TAG,"加载历史消息..");
//        new Handler().postDelayed(new Runnable() { //imitation of internet connection
//            @Override
//            public void run() {
//               loadMore();
//            }
//        }, 1000);
    }

    private void loadMore() {
        if (dialogId_ != null) {//之前有聊过天
            DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
            MessageDataDao messageDataDao = daoSession.getMessageDataDao();
            List<MessageData> messageDataList = messageDataDao.queryBuilder()
                    .where(MessageDataDao.Properties.DialogId.eq(dialogId_))
                    .orderDesc(MessageDataDao.Properties.UpdateDate).list();

            ArrayList<ChatMessage> messages = new ArrayList<>(messageDataList.size());

            for (MessageData messageData : messageDataList) {
                User user = getUser(messageData.getSenderId());
                ChatMessage message = new ChatMessage(messageData.getUuid(), user,
                        messageData.getContent(), messageData.getCreateDate());
                messages.add(message);
            }
            messagesAdapter.addToEnd(messages, false);
        }
    }

}
