package com.easy.fly.flyeasy.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.easy.fly.flyeasy.common.NetworkBoundResponse;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.Hotel;
import com.easy.fly.flyeasy.db.models.HotelBook;
import com.easy.fly.flyeasy.repositories.HotelRepository;
import com.easy.fly.flyeasy.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class HotelViewModel extends ViewModel {

    private HotelRepository hotelRepository;

    private UserRepository userRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    @Inject
    public HotelViewModel( HotelRepository hotelRepository,UserRepository userRepository){
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public MutableLiveData<Response> response() {
        return response;
    }

    public void findHotels(String authorization,long locationId){
        Observable<List<Hotel>> hotels = hotelRepository.findHotels(authorization, locationId);
        Observable<BasicModel> accessTokenGD = userRepository.getAccessTokenGD(authorization);
        loadHotels(hotels,accessTokenGD);

    }
    public void hotelBook(String authorization,long hotelRoomId,String amount){
        loadResultFromHotelBook(authorization,hotelRoomId,amount,hotelRepository);
    }

    public void getAllHotel(String authorization){
        Observable<List<Hotel>> hotels = hotelRepository.getAllHotel(authorization);
        Observable<BasicModel> accessTokenGD = userRepository.getAccessTokenGD(authorization);
        loadHotels(hotels,accessTokenGD);
    }

    private void loadHotels(Observable<List<Hotel>> allFlights, Observable<BasicModel> accessTokenGD) {
        new NetworkBoundResponse().getResponse(allFlights,accessTokenGD,disposables,response);

    }

    private void loadResultFromHotelBook(String authorization, long hotelRoomId, String amount, HotelRepository hotelRepository) {
        new NetworkBoundResponse().getResponse(authorization,hotelRoomId,amount,hotelRepository,disposables,response);
    }

}
