package com.funstill.netty.chat.netty;

import android.os.Build;
import android.util.Log;

import com.funstill.netty.chat.observer.ProtoMsgObservable;
import com.funstill.netty.chat.observer.ProtoMsgObserverImpl;
import com.funstill.netty.chat.protobuf.CommonMsg;
import com.funstill.netty.chat.protobuf.ProtoMsg;

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
    public final String TAG="NettyClientHandler";
    public static ProtoMsgObservable msgObservable=new ProtoMsgObservable();
    static {
        msgObservable.addObserver(new ProtoMsgObserverImpl());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        Log.d(TAG,"Android客户端连接,设备=" + Build.MODEL+",SERIAL=" + Build.SERIAL);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        Log.e(TAG,"连接已中断");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        msgObservable.handleMsg(ctx.channel(),(ProtoMsg.Content)msg);//相当于observer 的 update
//        ProtoMsg.Content pm = (ProtoMsg.Content) msg;
//        System.out.println("客户端接收消息" + pm.getContent().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        Log.e(TAG,"连接异常exceptionCaught="+cause.getMessage());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                //读超时
                Log.e(TAG, "===服务端=== （Reader_IDLE 读超时）");
                ctx.channel().close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                //写超时
                Log.e(TAG, "===服务端=== （Reader_IDLE 写超时）");
  //              ctx.channel().writeAndFlush();//TODO 发送心跳包
            } else if (event.state() == IdleState.ALL_IDLE) {
                //总超时
                Log.e(TAG, "===服务端=== （ALL_IDLE 总超时）");
                ctx.channel().close();

            }
        }
    }

    public static void sendMsg(String msgStr) {
        CommonMsg.Content.Builder body = CommonMsg.Content.newBuilder();
        body.setContent(msgStr);
        ProtoMsg.Content.Builder msgBuilder = ProtoMsg.Content.newBuilder();
        msgBuilder.setProtoType(1);
        msgBuilder.setContent(body.build().toByteString());
        channel.writeAndFlush(msgBuilder.build());
    }
}
