package com.easy.fly.flyeasy.repositories;

import android.arch.lifecycle.LiveData;

import com.easy.fly.flyeasy.AppExecutors;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.PictureResolution;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.dto.ChangeUserPasswordDto;
import com.easy.fly.flyeasy.dto.UpdateUserInformationDto;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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

    public Observable<User> getUser(String authorization){
        return userWebService.getUser(authorization);
    }

    public UserDB saveUserInDB(User user){
        UserDB of = UserDB.of(user);
        userDao.insert(of);

        return userDao.load(user.getId());
    }

    public UserDB loadUser(long userId){
        return userDao.load(userId);
    }

    public Observable<PictureResolution> uploadProfilePicture(String authorization, MultipartBody.Part photo){
        return userWebService.uploadProfilePicture(authorization,photo);
    }

    public Observable<User> updatePersonalInformation(String auhtorization,UpdateUserInformationDto updateUserInformationDto){
        return userWebService.updatePersonalInformation(auhtorization,updateUserInformationDto);
    }

    public Observable<List<FlightBooking>> myFlights(String authorization){
        return userWebService.myFlights(authorization);
    }

    public Observable<BasicModel> changeUserPassword(String authentication, ChangeUserPasswordDto changeUserPasswordDto){
        return userWebService.changePassword(authentication,changeUserPasswordDto);
    }

    public Observable<User> activateAccount(String token){
        return userWebService.activateAccount(token);
    }

}
