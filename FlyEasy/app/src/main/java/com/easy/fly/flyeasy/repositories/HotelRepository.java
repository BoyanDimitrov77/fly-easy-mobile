package com.easy.fly.flyeasy.repositories;

import com.easy.fly.flyeasy.AppExecutors;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.Hotel;
import com.easy.fly.flyeasy.db.models.HotelBook;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HotelRepository {
    private final UserDao userDao;
    private final UserWebService userWebService;
    private final AppExecutors appExecutors;

    @Inject
    public HotelRepository(UserDao userDao,UserWebService userWebService,AppExecutors appExecutors){
        this.userDao = userDao;
        this.userWebService = userWebService;
        this.appExecutors = appExecutors;
    }

    public Observable<List<Hotel>> findHotels(String authorization,long locationId){
        return userWebService.findHotels(authorization,locationId);
    }

    public Observable<HotelBook> bookHotel(String authorization,long hotelRoomId){
        return userWebService.bookHotel(authorization,hotelRoomId);
    }

    public Observable<HotelBook> payHotel(String authorization,String hotelBookId,String amount){
        return userWebService.payHotel(authorization,hotelBookId,amount);
    }

    public Observable<List<Hotel>>getAllHotel(String authorization){
        return userWebService.getAllHotel(authorization);
    }

    public Observable<List<HotelBook>>getAllMyBookedHotel(String authorization){
        return userWebService.myBookedHotels(authorization);
    }

}
