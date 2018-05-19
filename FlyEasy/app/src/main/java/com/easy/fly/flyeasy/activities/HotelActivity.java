package com.easy.fly.flyeasy.activities;

import android.arch.lifecycle.ViewModelProvider;
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
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.fragments.HotelListFragment;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HotelActivity extends AppCompatActivity implements HasSupportFragmentInjector{

    private String hotelScreenSelected;

    private long locationId;

    private UserViewModel viewModel;

    private SessionManager sessionManager;

    private String userAthenticationHeader;

    private UserDB userFromDB;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

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
        setContentView(R.layout.activity_hotel);
        ButterKnife.bind(this);

        //viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);

        //sessionManager = new SessionManager(this);

        initKey();

/*        //get user from Database
        Observable.fromCallable(() -> viewModel.getUserFromDB(UserUtil.getUserId(sessionManager.getUserDeatails())))
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(data->setUserFromDB(data));*/

        //NavigationDrawer
        //DrawerUtil.getDrawerProfileNavigation(this,userFromDB);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_hotel);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle bundle = new Bundle();
        bundle.putString("HOTEL_SCREEN_SELECTED",hotelScreenSelected);
        bundle.putLong("HOTEL_LOCATION_ID",locationId);

        HotelListFragment hotelListFragment = new HotelListFragment();
        hotelListFragment.setArguments(bundle);

        //start fragment
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        int containerId = R.id.container;
        fragmentManager.beginTransaction()
                .replace(containerId,hotelListFragment)
                .commitAllowingStateLoss();

    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public void initKey(){
        //userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
        hotelScreenSelected = getIntent().getExtras().get("HOTEL_SCREEN_SELECTED").toString();
        if(getIntent().getExtras().get("HOTEL_LOCATION_ID")!=null){
            locationId = Long.valueOf(getIntent().getExtras().get("HOTEL_LOCATION_ID").toString());
        }
        //userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());

    }

    private void setUserFromDB(UserDB userFromDB) {
        this.userFromDB = userFromDB;
    }
}
