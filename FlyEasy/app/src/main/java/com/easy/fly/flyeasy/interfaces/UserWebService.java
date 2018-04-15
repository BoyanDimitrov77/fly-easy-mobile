package com.easy.fly.flyeasy.interfaces;

import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.dto.PassengerDto;
import com.easy.fly.flyeasy.dto.SearchDto;
import com.easy.fly.flyeasy.dto.UserDto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

public interface UserWebService {
    @POST("register")
    Observable<User> regUser(@Body UserDto user);

    @GET("authenticate")
    Observable<User> authenticateUser(@Header("Authorization") String authorization);

    @GET("flight/all")
    Observable<List<Flight>> getAllFlihght(@Header("Authorization") String authorization);

    @POST("flight/searchFlights")
    Observable<List<Flight>> searchFlight(@Header("Authorization") String authorization,@Body SearchDto searchDto);

    @POST("booking/bookFlight/{flightId}")
    Observable<FlightBooking> bookFlight(@Header("Authorization") String authorization,@Path("flightId") long id);

    @POST("booking/bookFlight/passengers/{flightBookId}/{travelClassId}")
    Observable<FlightBooking> addPassengers(@Header("Authorization") String authorization, @Path("flightBookId") long flightBookId, @Path("travelClassId") long travelClassId, @Body List<PassengerDto> passengerDtos);

    @POST("booking/bookFlight/payment")
    Observable<FlightBooking> payBooking(@Header("Authorization") String authorization,@Query("amount") String amount , @Query("flightBookId") String flightBookId,@Query("bonusId") String bonusId,@Query("travelClassId") String travelClassId);


}
