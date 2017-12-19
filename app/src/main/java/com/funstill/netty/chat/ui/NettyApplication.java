package com.funstill.netty.chat.ui;

import android.app.Application;

import com.funstill.netty.chat.netty.NettyClientStarter;

/**
 * Created by liukaiyang on 2017/12/8.
 */

public class NettyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable(){
            @Override
            public void run() {
                new NettyClientStarter().connect(8089, "192.168.1.83");
            }
        }).start();

    }
}
