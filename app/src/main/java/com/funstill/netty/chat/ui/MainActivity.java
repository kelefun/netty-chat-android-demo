package com.funstill.netty.chat.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.config.Const;

public class MainActivity extends FragmentActivity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        String userId = sp.getString(Const.LOGIN_USER_ID, "");
        if (!TextUtils.isEmpty(userId)) {
            DefaultMessagesActivity.senderId=userId;
            //TODO 建立连接
            DefaultDialogsActivity.open(MainActivity.this);
        }else {
            LoginActivity.start(MainActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
