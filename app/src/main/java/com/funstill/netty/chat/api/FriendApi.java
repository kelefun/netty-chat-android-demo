package com.funstill.netty.chat.api;

import com.funstill.netty.chat.model.chat.ChatFriend;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Tamic on 2016-07-07.
 */
public interface FriendApi {

    @GET("friend/list")
    Observable<List<ChatFriend>> getFriendList(@QueryMap Map<String, Object> maps);
    @GET("friend/detail")
    Observable<ChatFriend> getFriendDetail(@QueryMap Map<String, Object> maps);

}
