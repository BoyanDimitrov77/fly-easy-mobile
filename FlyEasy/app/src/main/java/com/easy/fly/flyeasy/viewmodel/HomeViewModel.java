package com.easy.fly.flyeasy.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.easy.fly.flyeasy.common.NetworkBoundResponse;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.dto.SearchDto;
import com.easy.fly.flyeasy.repositories.FlightRepository;
import com.easy.fly.flyeasy.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class HomeViewModel  extends ViewModel{

    private FlightRepository flightRepository;

    private UserRepository userRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    @Inject
    public HomeViewModel(FlightRepository flightRepository, UserRepository userRepository){
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public MutableLiveData<Response> response() {
        return response;
    }

    public void allFlights(String authorization){
        Observable<List<Flight>> allFlights = flightRepository.getAllFlights(authorization);
        Observable<BasicModel> accessTokenGD = userRepository.getAccessTokenGD(authorization);
        loadFlights(allFlights,accessTokenGD);

    }
    public void searchFlights(String authorization, SearchDto searchDto){
        Observable<List<Flight>> searchFlight = flightRepository.getSearchFlight(authorization, searchDto);
        loadFlights(searchFlight);
    }

    public void getUser(String authorization){
        Observable<User> user = userRepository.getUser(authorization);
        Observable<BasicModel> accessTokenGD = userRepository.getAccessTokenGD(authorization);
        loadUserDetails(user,accessTokenGD);
    }

    public UserDB getUserFromDB(long userId){
        return userRepository.loadUser(userId);
    }

    private void loadUserDetails(Observable<User> user, Observable<BasicModel> accessTokenGD) {
        new NetworkBoundResponse().getResponse(user,accessTokenGD,disposables,response);
    }

    private void loadFlights(Observable<List<Flight>> allFlights) {
        new NetworkBoundResponse().getResponse(allFlights,disposables,response);
    }

    private void loadFlights(Observable<List<Flight>> allFlights, Observable<BasicModel> accessTokenGD) {
        new NetworkBoundResponse().getResponse(allFlights, accessTokenGD, disposables, response);

    }
}
