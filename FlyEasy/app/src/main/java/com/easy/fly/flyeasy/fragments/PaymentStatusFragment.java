package com.easy.fly.flyeasy.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.HotelActivity;
import com.easy.fly.flyeasy.activities.TicketActivity;
import com.easy.fly.flyeasy.databinding.FragmentPaymentStatusBinding;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.PaymentStatus;
import com.easy.fly.flyeasy.di.Injectable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentStatusFragment extends Fragment implements Injectable {

    private boolean isConfirmed;

    private boolean isPaymentStatusFormFlightBook;

    //private long locationId;

    private FlightBooking flightBooking;

    @BindView(R.id.avaialbleHotelsbtn)
    Button availableHotelButton;

    @BindView(R.id.bookedTicketsbtn)
    Button bookedTicketsButton;

    public PaymentStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        initKey();

        FragmentPaymentStatusBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment_status, container, false);
        final PaymentStatus paymentStatus = new PaymentStatus();
        PaymentStatus.setConfirmed(paymentStatus, isConfirmed);

        if(isPaymentStatusFormFlightBook){
            PaymentStatus.setPaymentStatusFromFlightBook(paymentStatus,true);
        }else{
            PaymentStatus.setPaymentStatusFromFlightBook(paymentStatus,false);
        }

        binding.setPaymentStatus(paymentStatus);

        ButterKnife.bind(this,binding.getRoot());

        availableHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelActivity.class);
                intent.putExtra("HOTEL_SCREEN_SELECTED","hotelScreenAfterSuccessfulPaymentBooking");
                intent.putExtra("HOTEL_LOCATION_ID",flightBooking.getFlight().getLocationTo().getId());

                startActivity(intent);
            }
        });

        bookedTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("FLIGHT_BOOKING",flightBooking);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private void initKey(){

        isConfirmed = getArguments().getBoolean("IS_CONFIRMED");
        flightBooking = (FlightBooking)getArguments().getParcelable("FLIGHT_BOOKING");
        //locationId = getArguments().getLong("HOTEL_LOCATION_ID");
        isPaymentStatusFormFlightBook = getArguments().getBoolean("IS_PAYMENT_STATUS_FROM_FLIGHT_BOOK");
    }

}
