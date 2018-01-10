package com.funstill.netty.chat.ui;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.funstill.generator.greendao.dao.DaoMaster;
import com.funstill.generator.greendao.dao.DaoMaster.DevOpenHelper;
import com.funstill.generator.greendao.dao.DaoSession;
import com.funstill.netty.chat.config.ServerConfig;
import com.funstill.netty.chat.netty.NettyClientStarter;
import com.funstill.netty.chat.websocket.DefaultWebsocketClient;

import org.greenrobot.greendao.database.Database;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by liukaiyang on 2017/12/8.
 */

public class NettyApplication extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        connectServer();
        initDao();

    }
    public DaoSession getDaoSession() {
        return daoSession;
    }
    public void initDao(){
        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
    public void connectServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new NettyClientStarter().connect(ServerConfig.NETTY_PORT, ServerConfig.NETTY_HOST);
                DefaultWebsocketClient c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
                try {
                    c = new DefaultWebsocketClient( new URI( "ws://localhost:8887" ));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                c.connect();
            }
        }).start();
    }
}
