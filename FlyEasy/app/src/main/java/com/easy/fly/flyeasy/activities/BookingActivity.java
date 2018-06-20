package com.easy.fly.flyeasy.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.NewsCountry;
import com.easy.fly.flyeasy.common.NewsParametersConstants;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.fragments.BookingFragment;
import com.easy.fly.flyeasy.utils.UserUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class BookingActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private SessionManager sessionManager;

    private String userAthenticationHeader;

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
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(getApplicationContext());

        userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());

        sessionManager.getUserDeatails();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        Flight flight = (Flight)getIntent().getExtras().getParcelable("FLIGHT");
        String accessTocketnGD = (String) getIntent().getExtras().get("ACCES_TOCKENT_GD");

        Bundle bundle = new Bundle();
        bundle.putParcelable("FLIGHT",getIntent().getExtras().getParcelable("FLIGHT"));
        bundle.putString("ACCES_TOCKENT_GD",accessTocketnGD);

        BookingFragment bookingFragment = new BookingFragment();
        bookingFragment.setArguments(bundle);

        //start fragment
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        int containerId = R.id.container;
        fragmentManager.beginTransaction()
                .replace(containerId,bookingFragment)
                .commitAllowingStateLoss();

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
