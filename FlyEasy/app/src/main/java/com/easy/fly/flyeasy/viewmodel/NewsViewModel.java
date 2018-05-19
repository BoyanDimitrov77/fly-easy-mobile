package com.easy.fly.flyeasy.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.easy.fly.flyeasy.common.NetworkBoundResponse;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.News;
import com.easy.fly.flyeasy.repositories.NewsRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class NewsViewModel extends ViewModel {

    private NewsRepository newsRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    @Inject
    public NewsViewModel (NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public MutableLiveData<Response> response() {
        return response;
    }


    public void allNewsByCountry(String country, String apiKey){
        Observable<News> allNewsByCountry = newsRepository.getAllNewsByCountry(country, apiKey);
        loadNews(allNewsByCountry);
    }

    public void allNewsBySource(String source,String apiKey){
        Observable<News> allNewsBySource = newsRepository.getAllNewsBySource(source, apiKey);
        loadNews(allNewsBySource);
    }

    public void allNewsByCountryAndCategory(String country, String category, String apiKye){
        Observable<News> allNewsByCountryAndCategory = newsRepository.getAllNewsByCountryAndCategory(country, category, apiKye);
        loadNews(allNewsByCountryAndCategory);
    }

    private void loadNews(Observable<News> news) {
        new NetworkBoundResponse().getResponse(news,disposables,response);
    }


}
