package com.funstill.netty.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.funstill.netty.chat.config.StoreConst;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by liukaiyang on 2018/1/16.
 */

public class AccountStoreUtil {
    private Context mContext;
    private static SharedPreferences mPreferences;

    public AccountStoreUtil(Context context) {
        this.mContext = context;
        this.mPreferences = mContext.getSharedPreferences(StoreConst.STORE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void put(String key, String value) {
        if (mPreferences == null) {
            throw new RuntimeException("没有初始化构造函数");
        }
        Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static synchronized void put(JsonObject jsonObject) {
        if (mPreferences == null) {
            throw new RuntimeException("没有初始化构造函数");
        }
        Editor editor = mPreferences.edit();
        for (Map.Entry<String, JsonElement> param : jsonObject.entrySet()) {
            editor.putString(param.getKey(), param.getValue().toString());
        }
        editor.commit();
    }

    public static synchronized void put(Map<String, String> params) {
        if (mPreferences == null) {
            throw new RuntimeException("没有初始化构造函数");
        }
        Editor editor = mPreferences.edit();
        for (Map.Entry<String, String> param : params.entrySet()) {
            editor.putString(param.getKey(), param.getValue());
        }
        editor.commit();
    }


    public static String get(String key) {
        if (mPreferences == null) {
            throw new RuntimeException("没有初始化构造函数");
        }
        return mPreferences.getString(key, "");
    }

    public synchronized void clear() {
        Editor editor = mPreferences.edit();
        editor.clear();
        editor.commit();
    }


}
