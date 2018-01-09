package com.funstill.netty.chat.api;

import com.funstill.netty.chat.model.chat.ChatUser;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tamic on 2016-07-07.
 */
public interface UserApi {

    @GET("user/detail")
    Observable<ChatUser> getUser(@Query("userId") Long userId);

}
