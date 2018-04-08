package com.easy.fly.flyeasy.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.easy.fly.flyeasy.common.NetworkBoundResponse;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.repositories.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

public class RegisterUserViewModel extends ViewModel{

    private UserRepository userRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    @Inject
    public RegisterUserViewModel(UserRepository userRepository){
        this.userRepository =userRepository;
    }

    public void registerUser(UserDto userDto){
        Observable<User> userObservable = userRepository.regUser(userDto);
        loadUserDetails(userObservable);

    }

    public void loginUser(String authorization){
        Observable<User> authenticate = userRepository.authenticate(authorization);
        authenticateUser(authenticate);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public MutableLiveData<Response> response() {
        return response;
    }

    private void loadUserDetails(Observable<User> userObservable){
         new NetworkBoundResponse().getResponse(userObservable,disposables,response);

    }

    private void authenticateUser(Observable<User> userObservable){
        new NetworkBoundResponse().getResponse(userObservable,disposables,response);

    }

}
