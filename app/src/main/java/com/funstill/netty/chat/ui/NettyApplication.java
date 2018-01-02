package com.funstill.netty.chat.ui;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.funstill.netty.chat.config.ServerConfig;
import com.funstill.netty.chat.netty.NettyClientStarter;

/**
 * Created by liukaiyang on 2017/12/8.
 */

public class NettyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new NettyClientStarter().connect(ServerConfig.NETTY_PORT, ServerConfig.NETTY_HOST);
            }
        }).start();

    }
}
