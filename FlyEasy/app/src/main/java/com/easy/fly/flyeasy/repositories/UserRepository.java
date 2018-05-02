package com.easy.fly.flyeasy.repositories;

import android.annotation.SuppressLint;

import com.easy.fly.flyeasy.AppExecutors;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    public Observable<User> regUser (final UserDto userDto){
        return userWebService.regUser(userDto);

    }

    public Observable<User> authenticate(String authorization){
        return userWebService.authenticateUser(authorization);
    }

    public Observable<BasicModel>getAccessTokenGD(String authorization){
        return userWebService.getAccessTokenGD(authorization);
    }


}
