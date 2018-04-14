package com.easy.fly.flyeasy.repositories;

import com.easy.fly.flyeasy.AppExecutors;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.dto.SearchDto;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FlightRepository {

    private final UserDao userDao;
    private final UserWebService userWebService;
    private final AppExecutors appExecutors;

    @Inject
    FlightRepository(UserDao userDao,UserWebService userWebService,AppExecutors appExecutors){
        this.userDao = userDao;
        this.userWebService = userWebService;
        this.appExecutors = appExecutors;
    }

    public Observable<List<Flight>> getAllFlights(String authorization){
        return userWebService.getAllFlihght(authorization);
    }

    public Observable<List<Flight>> getSearchFlight(String authorization, SearchDto searchDto){
        return userWebService.searchFlight(authorization,searchDto);
    }

}
