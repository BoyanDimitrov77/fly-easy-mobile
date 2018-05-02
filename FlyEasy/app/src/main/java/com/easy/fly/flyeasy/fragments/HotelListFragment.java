package com.easy.fly.flyeasy.fragments;


import android.app.SearchManager;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.FlightAdapter;
import com.easy.fly.flyeasy.adapters.HotelAdapter;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.Hotel;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.BookingViewModel;
import com.easy.fly.flyeasy.viewmodel.HotelViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelListFragment extends Fragment implements Injectable {

   private HotelViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.hotel_card_recycle_view)
    RecyclerView recyclerView;

    SearchView searchView;

    private HotelAdapter adapter;

    private CombineModel data;

    private SessionManager sessionManager;

    private String userAthenticationHeader;

    private String hotelScreenSelected;

    private long locationId;

    public HotelListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(HotelViewModel.class);
        View inflate = inflater.inflate(R.layout.fragment_hotel_list, container, false);
        ButterKnife.bind(this,inflate);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // toolbar fancy stuff
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.toolbar_title);

        setHasOptionsMenu(true);

        sessionManager = new SessionManager(getContext());
        initKey();

        //whiteNotificationBar(recyclerView);

        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        switch (hotelScreenSelected){
            case "hotelHomeScreen" :
                viewModel.getAllHotel(userAthenticationHeader);
                viewModel.response().observe(this,response -> processResponse(response));
                break;
            case "hotelScreenAfterSuccessfulPaymentBooking" :

                viewModel.findHotels(userAthenticationHeader,locationId);
                viewModel.response().observe(this,response -> processResponse(response));

                break;
        }



        return inflate;
    }

    private void processResponse(Response response) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                data = (CombineModel) response.data;
                adapter = new HotelAdapter(data,getContext(),this.getFragmentManager());
                recyclerView.setAdapter(adapter);
                break;

            case ERROR:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getActivity().getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    private void initKey(){
        userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
        locationId = getArguments().getLong("HOTEL_LOCATION_ID");
        hotelScreenSelected = getArguments().getString("HOTEL_SCREEN_SELECTED");
    }

}
