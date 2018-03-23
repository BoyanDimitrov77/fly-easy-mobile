package com.easy.fly.flyeasy.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.easy.fly.flyeasy.AppExecutors;
import com.easy.fly.flyeasy.common.ApiResponse;
import com.easy.fly.flyeasy.common.NetworkBoundResource;
import com.easy.fly.flyeasy.common.Resource;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

@Singleton
public class UserRepository {

    private final UserDao userDao;
    private final UserWebService userWebService;
    private final AppExecutors appExecutors;

    @Inject
    UserRepository(AppExecutors appExecutors, UserDao userDao, UserWebService userWebService) {
        this.userDao = userDao;
        this.userWebService = userWebService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<User>> loadUser(final String userId) {
        return new NetworkBoundResource<User,User>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.save(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
               // return rateLimiter.canFetch(userId) && (data == null || !isFresh(data));
                return false;
            }

            @NonNull @Override
            protected LiveData<User> loadFromDb() {
                return userDao.load(userId);
            }

            @NonNull @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return userWebService.getUser(userId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<UserDto>> registerUser(final UserDto userDto){
        return new NetworkBoundResource<UserDto,User>(appExecutors){
            @NonNull
            @Override
            protected LiveData<UserDto> loadFromDb() {
                return null;
            }

            @Override
            protected boolean shouldFetch(@Nullable UserDto data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return userWebService.registerUser(userDto);
            }

            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.save(item);
            }
        }.asLiveData();
    }
}
