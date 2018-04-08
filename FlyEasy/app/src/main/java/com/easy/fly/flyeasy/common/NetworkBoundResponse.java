package com.easy.fly.flyeasy.common;

import android.arch.lifecycle.MutableLiveData;

import com.easy.fly.flyeasy.db.models.User;

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
}
