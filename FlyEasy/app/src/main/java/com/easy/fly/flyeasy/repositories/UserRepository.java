package com.easy.fly.flyeasy.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Visibility;

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

import dagger.Module;
import dagger.Provides;

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
                return (LiveData<ApiResponse<User>>) userWebService.getUser(userId);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<User>> registerUser(final UserDto userDto){
        return new NetworkBoundResource<User,UserDto>(appExecutors){
            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return null;
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserDto>> createCall() {
                return (LiveData<ApiResponse<UserDto>>) userWebService.registerUser(userDto);
            }

            @Override
            protected void saveCallResult(@NonNull UserDto item) {
                userDao.save(User.of(item));
            }
        }.getAsLiveData();
    }
}
