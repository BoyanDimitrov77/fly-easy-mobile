package com.easy.fly.flyeasy.di;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

import android.app.Application;
import android.arch.persistence.room.Room;

import com.easy.fly.flyeasy.db.FlyEasyDatabase;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.interfaces.NewsWebService;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton @Provides
    UserWebService provideService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UserWebService.class);
    }

    @Singleton @Provides
    NewsWebService provideSevice(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsWebService.class);

    }

    @Singleton @Provides
    FlyEasyDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, FlyEasyDatabase.class,"flyeasy.db").build();
    }

    @Singleton @Provides
    UserDao provideUserDao(FlyEasyDatabase db) {
        return db.userDao();
    }

}
