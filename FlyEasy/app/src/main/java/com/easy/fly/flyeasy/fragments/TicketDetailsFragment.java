package com.easy.fly.flyeasy.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.TicketAdapter;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.di.Injectable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketDetailsFragment extends Fragment implements Injectable {

    private FlightBooking flightBooking;

    @BindView(R.id.ticket_recycler_view)
    RecyclerView recyclerView;

    private TicketAdapter adapter;


    public TicketDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_ticket_details, container, false);
        ButterKnife.bind(this,inflate);

        initKey();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        if(flightBooking != null){
            adapter = new TicketAdapter(flightBooking,getContext());
            recyclerView.setAdapter(adapter);
        }

        return inflate;
    }

    private void initKey(){
        flightBooking = (FlightBooking) getArguments().getParcelable("FLIGHT_BOOKING");
    }

}
