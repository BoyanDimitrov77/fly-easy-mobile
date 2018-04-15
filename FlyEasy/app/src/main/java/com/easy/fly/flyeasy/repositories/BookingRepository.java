package com.easy.fly.flyeasy.repositories;

import com.easy.fly.flyeasy.AppExecutors;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.dto.PassengerDto;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class BookingRepository {

    private final UserDao userDao;
    private final UserWebService userWebService;
    private final AppExecutors appExecutors;

    @Inject
    public BookingRepository(UserDao userDao,UserWebService userWebService,AppExecutors appExecutors){
        this.userDao = userDao;
        this.userWebService = userWebService;
        this.appExecutors = appExecutors;
    }

    public Observable<FlightBooking> bookFlight(String authorization,long flightId){
        return userWebService.bookFlight(authorization,flightId);
    }
    public Observable<FlightBooking> addPassengers(String authorization, long flightId, long travelClassId, List<PassengerDto> passengerDtos){
        return userWebService.addPassengers(authorization,flightId,travelClassId,passengerDtos);
    }

    public Observable<FlightBooking> payBooking(String authorization,String amount,String flightBookingId,String bonusId,String travelClassId){
        return userWebService.payBooking(authorization,amount,flightBookingId,bonusId,travelClassId);
    }

}
