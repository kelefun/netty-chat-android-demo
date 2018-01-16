package com.funstill.netty.chat.observer;

import android.util.Log;

import com.funstill.netty.chat.protobuf.ProtoMsg;

import io.netty.channel.Channel;

/**
 * 默认处理
 * @author liukaiyang
 * @date 2017/12/6 20:16
 */
public class ProtoMsgObserverImpl implements ProtoMsgObserver {

    @Override
    public void handleProtoMsg(Channel channel, ProtoMsg.Content msg) {
        Log.i("ProtoMsgObserverImpl",msg.getProtoType()+"=proto-type,收到消息...");
    }
}
