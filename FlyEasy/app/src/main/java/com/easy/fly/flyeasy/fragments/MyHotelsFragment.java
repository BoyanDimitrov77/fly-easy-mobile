package com.easy.fly.flyeasy.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.MyFlightsAdapter;
import com.easy.fly.flyeasy.adapters.MyHotelsAdapter;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.HotelViewModel;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHotelsFragment extends Fragment implements Injectable {

    private HotelViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.my_hotel_booked_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.profile_picture)
    CircleImageView profilePicture;

    @BindView(R.id.user_fullName)
    TextView userFullName;

    private SessionManager sessionManager;

    private String userAthenticationHeader;

    private CombineModel data;

    private MyHotelsAdapter adapter;

    private User user;


    public MyHotelsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my_hotels, container, false);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HotelViewModel.class);
        ButterKnife.bind(this,inflate);

        sessionManager  = new SessionManager(getContext());
        initKey();

        userFullName.setText(user.getFullName());
        if(user.getProfilePicture()!= null){
            Glide.with(getContext())
                    .load(HeaderAtuhenticationGlide.loadUrl(user.getProfilePicture().getThumbnailPicture().getValue()))// GlideUrl is created anyway so there's no extra objects allocated
                    .into(profilePicture);
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getAllMyBookedHotel(userAthenticationHeader);
        viewModel.response().observe(this,response -> processResponse(response));

        return inflate;
    }

    private void processResponse(Response response) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                data = (CombineModel) response.data;
                adapter = new MyHotelsAdapter(data,getContext());
                recyclerView.setAdapter(adapter);
                break;

            case ERROR:
                break;
        }
    }

    private void initKey(){
        userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
        user = (User)getArguments().getParcelable("USER");
    }

}
