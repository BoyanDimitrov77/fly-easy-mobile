package com.easy.fly.flyeasy.di;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

import com.easy.fly.flyeasy.fragments.BookingFragment;
import com.easy.fly.flyeasy.fragments.HotelDetailsFragment;
import com.easy.fly.flyeasy.fragments.HotelListFragment;
import com.easy.fly.flyeasy.fragments.PassengerFragment;
import com.easy.fly.flyeasy.fragments.PaymentFragment;
import com.easy.fly.flyeasy.fragments.PaymentStatusFragment;
import com.easy.fly.flyeasy.fragments.RegularRegistrationUserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract RegularRegistrationUserFragment contributeRegularRegistrationUserFragment();

    @ContributesAndroidInjector
    abstract BookingFragment contributeBookingFragment();

    @ContributesAndroidInjector
    abstract PassengerFragment contributePassengerFragment();

    @ContributesAndroidInjector
    abstract PaymentFragment contributePaymentFragment();

    @ContributesAndroidInjector
    abstract PaymentStatusFragment contributePaymentStatusFragment();

    @ContributesAndroidInjector
    abstract HotelListFragment contributeHotelListFragment();

    @ContributesAndroidInjector
    abstract HotelDetailsFragment contributeHotelDetailsFragment();

}
