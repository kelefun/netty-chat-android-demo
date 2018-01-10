package com.funstill.netty.chat.utils;

import com.funstill.netty.chat.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liukaiyang on 2018/1/10.
 */

public class RetrofitUtil {
    public static Retrofit retrofit(String baseUrl) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder().retryOnConnectionFailure(true);
        // 打印请求日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(logging);
        }

        OkHttpClient okHttpClient = okHttpBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
}
