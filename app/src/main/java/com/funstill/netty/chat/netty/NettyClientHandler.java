package com.funstill.netty.chat.netty;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.funstill.netty.chat.config.StoreConst;
import com.funstill.netty.chat.model.enums.ProtoTypeEnum;
import com.funstill.netty.chat.observer.ProtoMsgObservable;
import com.funstill.netty.chat.observer.ProtoMsgObserverImpl;
import com.funstill.netty.chat.protobuf.ProtoMsg;
import com.funstill.netty.chat.utils.AccountStoreUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author liukaiyang
 * @date 2017/12/5 15:04
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    public static Channel channel;
    public final String TAG = "NettyClientHandler";
    public static ProtoMsgObservable msgObservable = new ProtoMsgObservable();
    private final ProtoMsg.Content heartBeatPingMsg = ProtoMsg.Content.newBuilder()
            .setProtoType(ProtoTypeEnum.HEART_BEAT_PING.getIndex()).build();

    static {
        msgObservable.addObserver(new ProtoMsgObserverImpl());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        String userId=AccountStoreUtil.get(StoreConst.LOGIN_USER_ID);
        if(!TextUtils.isEmpty(userId)){
            //上线请求
            ProtoMsg.Content.Builder msgBuilder=ProtoMsg.Content.newBuilder();
            msgBuilder.setProtoType(ProtoTypeEnum.ONLINE_REQUEST_MSG.getIndex());
            channel.writeAndFlush(msgBuilder.build());
        }
        Log.d(TAG, "Android客户端连接,设备=" + Build.MODEL + ",SERIAL=" + Build.SERIAL);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        Log.e(TAG, "连接已中断,尝试重连");
        NettyClientStarter.getInstance().threadRun();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        msgObservable.handleMsg(ctx.channel(), (ProtoMsg.Content) msg);//相当于observer 的 update
//        ProtoMsg.Content pm = (ProtoMsg.Content) msg;
//        System.out.println("客户端接收消息" + pm.getContent().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        Log.e(TAG, "连接异常exceptionCaught=" + cause.getMessage());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                Log.d(TAG, "WRITER_IDLE超时,准备发送心跳包");
                ctx.channel().writeAndFlush(heartBeatPingMsg);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
