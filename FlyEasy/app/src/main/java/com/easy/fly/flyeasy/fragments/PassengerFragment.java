package com.easy.fly.flyeasy.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.PassengerAdapter;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.Bonuse;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.PassengerDto;
import com.easy.fly.flyeasy.viewmodel.BookingViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassengerFragment extends Fragment implements Injectable {

    @BindView(R.id.passenger_card_recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.passenger_button_next)
    Button passengerNextButton;

    private ArrayList<PassengerDto> data;

    private PassengerAdapter adapter;

    private String authHeader;

    private int ticketNumber;

    private long flightId;

    private long travelClassId;

    private String totalPriceTicket;

    private BookingViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    public PassengerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BookingViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_passenger, container, false);
        ButterKnife.bind(this,inflate);

        initKey();

        data = new ArrayList<>();
        for (int i =0 ; i<ticketNumber;i++){
            data.add(new PassengerDto("","",""));
        }
        adapter = new PassengerAdapter(data,getContext(),authHeader);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        passengerNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PassengerDto> passengers = adapter.getPassengers();
                System.out.println(passengers.toArray());

                viewModel.addPassenger(authHeader,flightId,travelClassId,passengers);
                viewModel.response().observe(getActivity(),response->processResponse(response,totalPriceTicket,travelClassId));

            }
        });


        return inflate;
    }

    private void processResponse(Response response, String totalPrice, long travelClassId) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:



                FlightBooking flightBooking = (FlightBooking) response.data;
                Bundle bundle = new Bundle();

                bundle.putString("AUTORIZATION",authHeader);
                bundle.putParcelable("FLIGHT_BOOKING",flightBooking);
                bundle.putString("TRAVEL_CLASS_ID" ,new Long(travelClassId).toString());
                bundle.putString("TOTAL_PRICE_TICKET",totalPrice);


                PaymentFragment paymentFragment = new PaymentFragment();
                paymentFragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = this.getFragmentManager();
                int containerId = R.id.container;
                fragmentManager.beginTransaction()
                        .replace(containerId,paymentFragment)
                        .commitAllowingStateLoss();

                break;

            case ERROR:
                break;
        }
    }


    private void  initKey(){
        authHeader = getArguments().getString("AUTORIZATION");
        ticketNumber = getArguments().getInt("TICKET_NUMBER");
        flightId = getArguments().getLong("FLIGHT_ID");
        travelClassId = getArguments().getLong("TRAVEL_CLASS_ID");
        totalPriceTicket = getArguments().getString("TOTAL_PRICE_TICKET");
    }

}
