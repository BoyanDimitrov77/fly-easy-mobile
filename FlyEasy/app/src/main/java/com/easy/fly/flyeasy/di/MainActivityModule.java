package com.easy.fly.flyeasy.di;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

import com.easy.fly.flyeasy.activities.LoginActivity;
import com.easy.fly.flyeasy.activities.SignUpActivity;

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



}
