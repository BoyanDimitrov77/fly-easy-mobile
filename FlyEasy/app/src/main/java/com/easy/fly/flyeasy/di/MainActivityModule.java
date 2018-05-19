package com.easy.fly.flyeasy.di;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

import com.easy.fly.flyeasy.activities.BookingActivity;
import com.easy.fly.flyeasy.activities.HomeActivity;
import com.easy.fly.flyeasy.activities.HotelActivity;
import com.easy.fly.flyeasy.activities.LoginActivity;
import com.easy.fly.flyeasy.activities.NewsActivity;
import com.easy.fly.flyeasy.activities.SettingActivity;
import com.easy.fly.flyeasy.activities.SignUpActivity;
import com.easy.fly.flyeasy.activities.UserProfileActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
/*    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();*/

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract SignUpActivity contributeSignUpActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract HomeActivity contributeHomeActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract BookingActivity contributeBookingActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract HotelActivity contributeHotelActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract UserProfileActivity contributeUserProfileActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract SettingActivity contributeSettingActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract NewsActivity contributeNewsActivity();

}
