package com.funstill.netty.chat.netty;

import android.util.Log;

import com.funstill.netty.chat.config.ServerConfig;
import com.funstill.netty.chat.protobuf.ProtoMsg;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author liukaiyang
 * @date 2017/12/5 15:00
 */
public class NettyClientStarter {
    private EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;
    private final String TAG = "NettyClientStarter";
    private static int retryCount = 0;//已尝试重连次数,重连成功后归零
    private static NettyClientStarter singleton = null;

    public static NettyClientStarter getInstance() {
        if (singleton == null) {
            singleton = new NettyClientStarter();
        }
        return singleton;
    }

    public void threadRun(){
        new Thread(new Runnable() {
            @Override
            public void run() {
               connect(ServerConfig.NETTY_PORT, ServerConfig.NETTY_HOST);
            }
        }).start();
    }
    public void connect(int port, String host) {
        try {
            if (bootstrap == null) {
                init();
            }
            doConnect(port, host);
        } catch (Exception e) {
            Log.e("netty连接异常", e.toString());
            throw new RuntimeException(e);
        }
    }

    private void init() {
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(0, 8, 0));
                        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        ch.pipeline().addLast(new ProtobufDecoder(ProtoMsg.Content.getDefaultInstance()));
                        ch.pipeline().addLast(new ProtobufEncoder());
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }

    private void doConnect(int port, String host) {
        if (channel != null && channel.isActive()) {
            return;
        }
        final ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    Log.i(TAG, "连接成功-Connect to server successfully!");
                    channel = future.channel();
                    retryCount = 0;
                } else {
                    if (retryCount < 6) {
                        retryCount++;
                        Log.i(TAG, "连接失败," + (2 << retryCount) + "秒后将尝试重连,这是第[" + retryCount + "]次重连");
                        future.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                doConnect(port, host);
                            }
                        }, 2 << retryCount, TimeUnit.SECONDS);
                    }
                }
            }
        });
    }
}
