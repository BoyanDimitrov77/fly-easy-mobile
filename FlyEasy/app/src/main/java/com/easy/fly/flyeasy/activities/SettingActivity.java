package com.easy.fly.flyeasy.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.fragments.SettingFragment;
import com.easy.fly.flyeasy.fragments.UserPersonalInformationFragment;

import java.text.DateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SettingActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    return true;
                case R.id.navigation_hotel:
                    Intent intent = new Intent(getApplicationContext(),HotelActivity.class);
                    intent.putExtra("HOTEL_SCREEN_SELECTED","hotelHomeScreen");
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        UserDB user = (UserDB)getIntent().getExtras().getParcelable("USER");

        Bundle bundle = new Bundle();
        bundle.putParcelable("USER",user);

        SettingFragment settingFragment = new SettingFragment();
        settingFragment.setArguments(bundle);
        //start fragment
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        int containerId = R.id.container;
        fragmentManager.beginTransaction()
                .replace(containerId, settingFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

}
