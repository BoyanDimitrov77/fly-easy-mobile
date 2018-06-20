package com.easy.fly.flyeasy.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.fragments.BookingFragment;
import com.easy.fly.flyeasy.fragments.ForgetPasswordChangeFragment;
import com.easy.fly.flyeasy.fragments.SendEmailForgetPasswordFragment;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ForgetPasswordActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if(intent != null && intent.getData() != null){
            String token = intent.getData().getQueryParameter("token");

            Bundle bundle = new Bundle();
            bundle.putString("TOKEN",token);

            ForgetPasswordChangeFragment forgetPasswordChangeFragment = new ForgetPasswordChangeFragment();
            forgetPasswordChangeFragment.setArguments(bundle);

            //start fragment
            android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
            int containerId = R.id.container;
            fragmentManager.beginTransaction()
                    .replace(containerId,forgetPasswordChangeFragment)
                    .commitAllowingStateLoss();
        }else{

            SendEmailForgetPasswordFragment sendEmailForgetPasswordFragment = new SendEmailForgetPasswordFragment();

            //start fragment
            android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
            int containerId = R.id.container;
            fragmentManager.beginTransaction()
                    .replace(containerId,sendEmailForgetPasswordFragment)
                    .commitAllowingStateLoss();
        }

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
