package com.easy.fly.flyeasy.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.FlightAdapter;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.fragments.UserPersonalInformationFragment;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.HomeViewModel;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class UserProfileActivity extends AppCompatActivity implements HasSupportFragmentInjector{

    private UserViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private SessionManager sessionManager;

    private String userAthenticationHeader;

    private String key;

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
        setContentView(R.layout.activity_user_profile);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);

        initKey();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewModel.getUser(userAthenticationHeader);
        viewModel.response().observe(this,response -> processResponse(response));

    }

    private void processResponse(Response response) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                switch(key){
                    case "USER_PERSONAL_INFORMATION":
                        //UserDB user = (UserDB)getIntent().getExtras().getParcelable("USER");
                        User user = (User)response.data;

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("USER",user);
                        bundle.putBoolean("IS_EDIT",false);

                        UserPersonalInformationFragment userPersonalInformationFragment = new UserPersonalInformationFragment();
                        userPersonalInformationFragment.setArguments(bundle);
                        //start fragment
                        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
                        int containerId = R.id.container;
                        fragmentManager.beginTransaction()
                                .replace(containerId, userPersonalInformationFragment)
                                .commitAllowingStateLoss();

                        break;
                }

                break;

            case ERROR:
                break;
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void initKey(){
        userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
        key = (String)getIntent().getExtras().get("KEY");
    }
}
