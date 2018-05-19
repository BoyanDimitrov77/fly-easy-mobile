package com.easy.fly.flyeasy.repositories;

import com.easy.fly.flyeasy.AppExecutors;
import com.easy.fly.flyeasy.db.models.News;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.interfaces.NewsWebService;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NewsRepository {

    private final NewsWebService newsWebService;

    private final AppExecutors appExecutors;

    @Inject
    public NewsRepository(NewsWebService newsWebService, AppExecutors appExecutors){
        this.newsWebService = newsWebService;
        this.appExecutors = appExecutors;
    }

    public Observable<News> getAllNewsByCountry (String country, String apiKey){
        return  newsWebService.getAllNewsByCountry(country,apiKey);
    }

    public Observable<News> getAllNewsBySource(String source, String apiKey){
        return newsWebService.getAllNewsBySource(source,apiKey);
    }

    public Observable<News> getAllNewsByCountryAndCategory(String country, String category, String apiKey ){
        return newsWebService.getAllNewsByCountryAndCategory(country,category,apiKey);
    }
}
