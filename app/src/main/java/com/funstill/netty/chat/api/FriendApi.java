package com.funstill.netty.chat.api;

import com.funstill.netty.chat.model.chat.ChatFriend;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Tamic on 2016-07-07.
 */
public interface FriendApi {

    @GET("friend/list")
    Observable<List<ChatFriend>> getFriendList(@QueryMap Map<String, Object> maps);

}
