package com.funstill.netty.chat.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.funstill.netty.chat.R;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginActivity.start(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
