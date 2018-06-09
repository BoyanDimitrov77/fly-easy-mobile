package com.easy.fly.flyeasy.di;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

import com.easy.fly.flyeasy.fragments.BookingFragment;
import com.easy.fly.flyeasy.fragments.ChangePasswordFragment;
import com.easy.fly.flyeasy.fragments.HotelDetailsFragment;
import com.easy.fly.flyeasy.fragments.HotelListFragment;
import com.easy.fly.flyeasy.fragments.MyFlightsFragment;
import com.easy.fly.flyeasy.fragments.MyHotelsFragment;
import com.easy.fly.flyeasy.fragments.NewsDetailsFragment;
import com.easy.fly.flyeasy.fragments.PassengerFragment;
import com.easy.fly.flyeasy.fragments.PaymentFragment;
import com.easy.fly.flyeasy.fragments.PaymentStatusFragment;
import com.easy.fly.flyeasy.fragments.ReadArticleFragment;
import com.easy.fly.flyeasy.fragments.RegularRegistrationUserFragment;
import com.easy.fly.flyeasy.fragments.SettingFragment;
import com.easy.fly.flyeasy.fragments.TicketDetailsFragment;
import com.easy.fly.flyeasy.fragments.UserPersonalInformationFragment;

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

    @ContributesAndroidInjector
    abstract UserPersonalInformationFragment contributeUserPersonalInformationFragment();

    @ContributesAndroidInjector
    abstract SettingFragment contributeSettingFragment();

    @ContributesAndroidInjector
    abstract ChangePasswordFragment contributeChangePasswordFragment();

    @ContributesAndroidInjector
    abstract NewsDetailsFragment contributeNewsDetailsFragment();

    @ContributesAndroidInjector
    abstract ReadArticleFragment contributeReadArticleFragment();

    @ContributesAndroidInjector
    abstract MyFlightsFragment contributeMyFlightsFragment();

    @ContributesAndroidInjector
    abstract TicketDetailsFragment contributeTicketDetailsFragment();

    @ContributesAndroidInjector
    abstract MyHotelsFragment contributeMyHotelsFragment();

}
