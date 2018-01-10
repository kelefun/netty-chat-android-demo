package com.funstill.netty.chat.api;

import com.funstill.netty.chat.model.chat.ChatFriend;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Tamic on 2016-07-07.
 */
public interface FriendApi {

    @GET("friend/list")
    Call<List<ChatFriend>> getFriendList(@QueryMap Map<String, Object> maps);
    @GET("friend/detail")
    Call<ChatFriend> getFriendDetail(@Query("userId") Long userId,@Query("friendId") Long friendId);

}
