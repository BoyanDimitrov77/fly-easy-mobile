package com.easy.fly.flyeasy.interfaces;

import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.dto.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

public interface UserWebService {
    @POST("register")
    Call<User> registerUser(@Body UserDto dto);

    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String userId);
}
