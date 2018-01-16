package com.funstill.netty.chat.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.config.StoreConst;
import com.funstill.netty.chat.utils.AccountStoreUtil;

public class MainActivity extends FragmentActivity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountStoreUtil accountStoreUtil= new AccountStoreUtil(this);
        String userId=accountStoreUtil.get(StoreConst.LOGIN_USER_ID);
        if (!TextUtils.isEmpty(userId)) {
            DefaultMessagesActivity.senderId=userId;
            DefaultDialogsActivity.open(MainActivity.this);
        }else {
            LoginActivity.start(MainActivity.this);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
