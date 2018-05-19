package com.easy.fly.flyeasy.interfaces;

import com.easy.fly.flyeasy.db.models.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsWebService {

    @GET("top-headlines")
    Observable<News> getAllNewsByCountry(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Observable<News> getAllNewsBySource(@Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Observable<News> getAllNewsByCountryAndCategory(@Query("country") String country, @Query("category") String category,@Query("apiKey") String apiKey);

}
