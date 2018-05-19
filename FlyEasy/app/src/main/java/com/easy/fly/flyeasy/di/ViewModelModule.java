package com.easy.fly.flyeasy.di;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.easy.fly.flyeasy.viewmodel.AppViewModelFactory;
import com.easy.fly.flyeasy.viewmodel.BookingViewModel;
import com.easy.fly.flyeasy.viewmodel.HomeViewModel;
import com.easy.fly.flyeasy.viewmodel.HotelViewModel;
import com.easy.fly.flyeasy.viewmodel.NewsViewModel;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindRegisterUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BookingViewModel.class)
    abstract ViewModel bindBookingViewModel(BookingViewModel bookingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HotelViewModel.class)
    abstract ViewModel bindHotelViewModel(HotelViewModel hotelViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindNewsViewModel(NewsViewModel newsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}
