package com.easy.fly.flyeasy.activities;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.utils.DateFormater;
import com.easy.fly.flyeasy.adapters.FlightAdapter;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.Flight;

import com.easy.fly.flyeasy.dto.SearchDto;
import com.easy.fly.flyeasy.fragments.DatePickerFragment;
import com.easy.fly.flyeasy.viewmodel.HomeViewModel;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HomeActivity extends AppCompatActivity implements HasSupportFragmentInjector,DatePickerDialog.OnDateSetListener {

    private TextView mTextMessage;

    private String authHeader;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private HomeViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.selectedDate)
    TextView selectedDate;

    @BindView(R.id.searchButton)
    Button searchButton;

    @BindView(R.id.fromLocation)
    EditText fromLocation;

    @BindView(R.id.toLocation)
    EditText toLocation;

    @BindView(R.id.priceSwitch)
    Switch priceSwitch;

    @BindView(R.id.ratingSwitch)
    Switch ratingSwitch;

    private ArrayList<Flight> data;
    private FlightAdapter adapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_news);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        ButterKnife.bind(this);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        authHeader = getIntent().getStringExtra("AUTORIZATION");


        viewModel.allFlights(authHeader);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogDatePicker =new DatePickerFragment();
                dialogDatePicker.show(getSupportFragmentManager(),"selectedDate picker");
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locationFrom = fromLocation.getText().toString();
                String locationTo = toLocation.getText().toString();
                String date = DateFormater.formatDate(selectedDate.getText().toString());
                Boolean sortByPrice= priceSwitch.isChecked();
                Boolean sortByRating = ratingSwitch.isChecked();

                viewModel.searchFlights(authHeader,new SearchDto(locationFrom,locationTo,date,sortByPrice,sortByRating));
            }
        });
        viewModel.response().observe(this,response -> processResponse(response));

    }

    private void processResponse(Response response) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                data = (ArrayList<Flight>) response.data;
                adapter = new FlightAdapter(data,getApplicationContext());
                recyclerView.setAdapter(adapter);
                break;

            case ERROR:
                break;
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        selectedDate.setText(currentDate);

    }
}