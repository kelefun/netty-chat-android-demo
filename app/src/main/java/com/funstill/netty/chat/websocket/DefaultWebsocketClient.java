package com.funstill.netty.chat.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by liukaiyang on 2018/1/10.
 */

public class DefaultWebsocketClient extends WebSocketClient {
    public DefaultWebsocketClient( URI serverURI ) {
        super( serverURI );
    }
    public DefaultWebsocketClient(URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i("DefaultWebsocketClient", "连接开启" );
    }

    @Override
    public void onMessage(String message) {
        Log.i("DefaultWebsocketClient", "收到消息" );
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.i("DefaultWebsocketClient", "关闭"+ "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
    }

    @Override
    public void onError(Exception ex) {
        Log.i("DefaultWebsocketClient", "连接错误" );
        ex.printStackTrace();
    }
}
