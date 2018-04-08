package com.easy.fly.flyeasy.interfaces;

import android.arch.lifecycle.LiveData;

import com.easy.fly.flyeasy.common.ApiResponse;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.dto.UserDto;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

public interface UserWebService {
    @POST("register")
    Observable<User> regUser(@Body UserDto user);

    @GET("authenticate")
    Observable<User> authenticateUser(@Header("Authorization") String authorization);


}
