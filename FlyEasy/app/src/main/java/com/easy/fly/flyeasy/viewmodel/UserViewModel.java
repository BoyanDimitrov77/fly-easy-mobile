package com.easy.fly.flyeasy.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.easy.fly.flyeasy.common.NetworkBoundResponse;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.PictureResolution;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.dto.ChangeUserPasswordDto;
import com.easy.fly.flyeasy.dto.UpdateUserInformationDto;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

public class UserViewModel extends ViewModel{

    private UserRepository userRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    @Inject
    public UserViewModel(UserRepository userRepository){
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

    public UserDB saveUserInDB(User user){
        UserDB savedUser = userRepository.saveUserInDB(user);
        Log.d("SavedUser in DB",savedUser.getUsername());
        return savedUser;
    }

    public void getAccessTockeGD(String authorization){
        Observable<BasicModel> accessTokenGD = userRepository.getAccessTokenGD(authorization);
        loadAccessTockentGD(accessTokenGD);

    }

    public void updatePesonalInformation(String authorization,UpdateUserInformationDto updateUserInformationDto){
        Observable<User> updateUserInformationDtoObservable = userRepository.updatePersonalInformation(authorization,updateUserInformationDto);
        loadUserUpdateInformation(updateUserInformationDtoObservable);
    }

    public void getUser(String authorization){
        Observable<User> user = userRepository.getUser(authorization);
        loadUserDetails(user);
    }

    public void myFlights(String authorization){
        Observable<List<FlightBooking>> flightBookingObservable = userRepository.myFlights(authorization);
        Observable<BasicModel> accessTokenGD = userRepository.getAccessTokenGD(authorization);
        loadUserFlights(flightBookingObservable,accessTokenGD);
    }

    public void changeUserPassword(String authentication, ChangeUserPasswordDto changeUserPasswordDto){
        Observable<BasicModel> userPasswordObservable = userRepository.changeUserPassword(authentication, changeUserPasswordDto);
        changePasswordUser(userPasswordObservable,disposables, response);
    }


    public UserDB getUserFromDB(long userId){
        return userRepository.loadUser(userId);
    }

    public void uploadProfilePicture(String authorization, MultipartBody.Part photo){
        Observable<PictureResolution> pictureResolutionObservable = userRepository.uploadProfilePicture(authorization, photo);
        loadPictureDetails(pictureResolutionObservable);
    }

    private void loadPictureDetails(Observable<PictureResolution> pictureResolutionObservable) {
        new NetworkBoundResponse().getResponse(pictureResolutionObservable, disposables, response);
    }

    private void loadUserFlights(Observable<List<FlightBooking>> flightBookingObservable, Observable<BasicModel> accessTokenGD) {
        new NetworkBoundResponse().getResponse(flightBookingObservable, accessTokenGD, disposables, response);
    }

    private void loadUserUpdateInformation(Observable<User> updateUserInformationDtoObservable) {
        new NetworkBoundResponse().getResponse(updateUserInformationDtoObservable,disposables,response);
    }

    private void loadAccessTockentGD(Observable<BasicModel> accessTokenGD) {
        new NetworkBoundResponse().getResponse(accessTokenGD,disposables,response);
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

    private void changePasswordUser(Observable<BasicModel> userPasswordObservable, CompositeDisposable disposables, MutableLiveData<Response> response) {
        new NetworkBoundResponse().getResponse(userPasswordObservable,disposables,response);
    }

}
