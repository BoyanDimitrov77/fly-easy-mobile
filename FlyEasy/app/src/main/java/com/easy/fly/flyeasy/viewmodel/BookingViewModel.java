package com.easy.fly.flyeasy.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.easy.fly.flyeasy.common.NetworkBoundResponse;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.dto.PassengerDto;
import com.easy.fly.flyeasy.repositories.BookingRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class BookingViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    private BookingRepository bookingRepository;

    @Inject
    public BookingViewModel(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public MutableLiveData<Response> response() {
        return response;
    }


    public void bookFlight(String auhorization,long flightId){
        Observable<FlightBooking> flightBookingObservable = bookingRepository.bookFlight(auhorization, flightId);
        loadResult(flightBookingObservable);
    }

    public void addPassenger(String auhorization, long flightId, long travelClassId, List<PassengerDto> passengerDtos){
        Observable<FlightBooking> flightBookingObservable = bookingRepository.addPassengers(auhorization, flightId, travelClassId, passengerDtos);
        loadResult(flightBookingObservable);
    }

    public void payBooking(String authorization,String amount,String flightBookingId,String bonusId,String travelClassId){
        Observable<FlightBooking> flightBookingObservable = bookingRepository.payBooking(authorization, amount,flightBookingId, bonusId, travelClassId);
        loadResult(flightBookingObservable);
    }

    private void loadResult(Observable<FlightBooking> flightBookingObservable) {
        new NetworkBoundResponse().getResponse(flightBookingObservable,disposables,response);
    }
}
