package com.easy.fly.flyeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.NewsCountry;
import com.easy.fly.flyeasy.common.NewsParametersConstants;
import com.easy.fly.flyeasy.fragments.NewsDetailsFragment;
import com.easy.fly.flyeasy.utils.DrawerUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class NewsActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private String category;
    private String countryCode;

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
                case R.id.navigation_news:
                    Intent intentN = new Intent(getApplicationContext(),NewsActivity.class);
                    intentN.putExtra("CATEGORY", NewsParametersConstants.ALL);
                    intentN.putExtra("COUNTRY", NewsCountry.USA.toString());
                    startActivity(intentN);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initKey();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_news);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle bundle = new Bundle();
        bundle.putString("CATEGORY",category);
        bundle.putString("COUNTRY",countryCode);

        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        newsDetailsFragment.setArguments(bundle);
        //start fragment
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        int containerId = R.id.container;
        fragmentManager.beginTransaction()
                .replace(containerId,newsDetailsFragment)
                .commitAllowingStateLoss();

        DrawerUtil.getDrawerNewsCategoryNavigation(this);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void initKey(){
        category = (String)getIntent().getExtras().get("CATEGORY");
        countryCode = (String)getIntent().getExtras().get("COUNTRY");
    }

}
