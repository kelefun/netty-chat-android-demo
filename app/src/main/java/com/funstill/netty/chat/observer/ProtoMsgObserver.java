package com.funstill.netty.chat.observer;

import com.funstill.netty.chat.protobuf.ProtoMsg;
import io.netty.channel.Channel;

/**
 * @author liukaiyang
 * @date 2017/12/6 10:15
 */
public interface ProtoMsgObserver{

    void handleProtoMsg(Channel channel, ProtoMsg.Content msg);
}
