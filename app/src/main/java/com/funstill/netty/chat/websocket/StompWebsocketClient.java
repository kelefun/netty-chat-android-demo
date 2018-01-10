package com.funstill.netty.chat.websocket;

import android.os.Handler;
import android.util.Log;

import com.funstill.netty.chat.websocket.stomp.Stomp;
import com.funstill.netty.chat.websocket.stomp.client.StompClient;

import org.java_websocket.WebSocket;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liukaiyang on 2018/1/10.
 */

public class StompWebsocketClient {

    private static final String TAG = "StompWebsocketClient";
    private StompClient mStompClient;


    private void test() {
        connectStomp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendEchoViaStomp();
            }
        }, 200);
    }
    public void connectStomp() {
        mStompClient = Stomp.over(WebSocket.class, "ws://192.168.1.83:80/youqing");

        mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.d(TAG,"Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            Log.d(TAG,"Stomp connection closed");
                    }
                });

        // Receive greetings
        mStompClient.topic("/user/queue/here/test")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d(TAG, "Received " + topicMessage.getPayload());
                });

        mStompClient.connect();
    }

    public void sendEchoViaStomp() {
        Log.d(TAG,"开始发送");
        mStompClient.send("/topic/test", "{}")
                .compose(applySchedulers())
                .subscribe(aVoid -> {
                    Log.d(TAG, "STOMP echo send successfully");
                }, throwable -> {
                    Log.e(TAG, "Error send STOMP echo", throwable);
                });
    }

    protected <T> FlowableTransformer<T, T> applySchedulers() {
        return tFlowable -> tFlowable
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
