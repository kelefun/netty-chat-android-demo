package com.funstill.netty.chat.ui;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.funstill.generator.greendao.dao.DaoMaster;
import com.funstill.generator.greendao.dao.DaoMaster.DevOpenHelper;
import com.funstill.generator.greendao.dao.DaoSession;
import com.funstill.netty.chat.netty.NettyClientStarter;
import com.funstill.netty.chat.utils.AccountStoreUtil;

import org.greenrobot.greendao.database.Database;

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
        new AccountStoreUtil(this);//初始化mPreferences
        NettyClientStarter.getInstance().threadRun();
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

}
