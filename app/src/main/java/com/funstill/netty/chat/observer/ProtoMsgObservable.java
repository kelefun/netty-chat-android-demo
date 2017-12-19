package com.funstill.netty.chat.observer;

import com.funstill.netty.chat.protobuf.ProtoMsg;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.Channel;

/**
 * @author liukaiyang
 * @date 2017/12/6 10:15
 */
public class ProtoMsgObservable extends BaseObservable {

    public void notifyObservers(Channel channel, ProtoMsg.Content msg) throws InvalidProtocolBufferException {
        Object[] arrLocal;
        synchronized (this) {
            if (!hasChanged())
                return;
            arrLocal = getObservers().toArray();
            clearChanged();
        }
        for (int i = arrLocal.length - 1; i >= 0; i--) {
            ((ProtoMsgObserver) arrLocal[i]).handleProtoMsg(channel, msg);
        }
    }

    public void handleMsg(Channel channel, ProtoMsg.Content msg) throws InvalidProtocolBufferException {
        setChanged();
        notifyObservers(channel, msg);
    }
}
