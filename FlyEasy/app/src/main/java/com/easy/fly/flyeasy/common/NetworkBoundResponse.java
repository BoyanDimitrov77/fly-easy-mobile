package com.easy.fly.flyeasy.common;

import android.arch.lifecycle.MutableLiveData;

import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.repositories.HotelRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NetworkBoundResponse<T> {

    public void getResponse(Observable<T> observable, CompositeDisposable disposables, MutableLiveData<Response> response) {
        disposables.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        data -> response.setValue(Response.success(data)),
                        throwable -> response.setValue(Response.error(throwable))
                )
        );
    }

    public void getResponse1(Observable<T> observable, CompositeDisposable disposables, MutableLiveData<Response> response) {
        disposables.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        data -> response.setValue(Response.success(data)),
                        throwable -> response.setValue(Response.error(throwable))
                )
        );
    }

    public void getResponse(Observable<List<T>> observable1, Observable<T> observable2, CompositeDisposable disposables, MutableLiveData<Response> response) {
        disposables.add(
                observable1.flatMap(fristResponse -> observable2,(fristResponse,secondResponse)-> new CombineModel(fristResponse,secondResponse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        data -> response.setValue(Response.success(data)),
                        throwable -> response.setValue(Response.error(throwable))
                )
        );
    }

    public void getResponse(String authorization, long hotelRoomId, String amount, HotelRepository hotelRepository, CompositeDisposable disposables, MutableLiveData<Response> response) {
        disposables.add(
                hotelRepository.bookHotel(authorization,hotelRoomId)
                        .flatMap(hotelBookResponse -> hotelRepository.payHotel(authorization,String.valueOf(hotelBookResponse.getId()),amount),
                        (hotelBookResponse,paymentHotelResponse)->paymentHotelResponse)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> response.setValue(Response.loading()))
                        .subscribe(
                                data -> response.setValue(Response.success(data)),
                                throwable -> response.setValue(Response.error(throwable))
                        )
        );
    }
}
