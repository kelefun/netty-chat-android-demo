package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.fixtures.MessagesFixtures;
import com.funstill.netty.chat.model.chat.ChatMessage;
import com.funstill.netty.chat.model.enums.ProtoTypeEnum;
import com.funstill.netty.chat.model.User;
import com.funstill.netty.chat.netty.NettyClientHandler;
import com.funstill.netty.chat.observer.ProtoMsgObserver;
import com.funstill.netty.chat.protobuf.CommonMsg;
import com.funstill.netty.chat.protobuf.ProtoMsg;
import com.funstill.netty.chat.utils.AppUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import io.netty.channel.Channel;

public class DefaultMessagesActivity extends MessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, DefaultMessagesActivity.class));
    }
    private ProtoMsgObserver commonMsgObserver = null;
    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_messages);
        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();
        initObserver();
        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
    }
    private void initObserver(){
        if(commonMsgObserver==null){
            commonMsgObserver=new ProtoMsgObserver() {
                @Override
                public void handleProtoMsg(Channel channel, ProtoMsg.Content msg) {
                    if(msg.getProtoType()== ProtoTypeEnum.COMMON_MSG.getIndex()){
                        CommonMsg.Content res;
                        try {
                            res=CommonMsg.Content.parseFrom(msg.getContent());
                            if(res.getMsgType()==1){
                                User user=new User(res.getSender()+"",res.getSender()+"","http://i.imgur.com/pv1tBmT.png",true);
                                ChatMessage chatKitMsg=new ChatMessage(msg.getUuid(),user , res.getContent());
                                messagesAdapter.addToStart(chatKitMsg,true);
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
    @Override
    public boolean onSubmit(CharSequence input) {
        ChatMessage msg = MessagesFixtures.getTextMessage(input.toString());
        super.messagesAdapter.addToStart(msg, true);
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
}
