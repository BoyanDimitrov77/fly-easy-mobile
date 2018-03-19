package com.easy.fly.flyeasy.di;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

import android.app.Application;
import android.arch.persistence.room.Room;

import com.easy.fly.flyeasy.FlyEasyApp;
import com.easy.fly.flyeasy.db.FlyEasyDatabase;
import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.interfaces.UserWebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory.*;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton @Provides
    UserWebService provideService() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserWebService.class);
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
