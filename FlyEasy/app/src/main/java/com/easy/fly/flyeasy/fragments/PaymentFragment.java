package com.easy.fly.flyeasy.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.Bonuse;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.viewmodel.BookingViewModel;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements Injectable {

    private String autheader;

    private String travelClassId;

    private FlightBooking flightBooking;

    private String totalPriceTicket;


    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.editBonus)
    EditText editBonus;

    @BindView(R.id.total_price)
    TextView totalPrice;

    @BindView(R.id.payButton)
    Button payButton;


    private BookingViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    public PaymentFragment() {
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
        View inflate = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this,inflate);

        initKey();

        BigDecimal bonuses = flightBooking.getBooker().getBonuses().size() !=0 ? flightBooking.getBooker().getBonuses().stream().map(Bonuse::getPercent).reduce(BigDecimal.ZERO, BigDecimal::add) : new BigDecimal(0);
        price.setText(totalPriceTicket);
        editBonus.setHint(bonuses.toString());


        totalPrice.setText(bonuses.compareTo(BigDecimal.ZERO) == 0 ? totalPriceTicket.toString()
                : new BigDecimal(totalPriceTicket).multiply(bonuses).divide(new BigDecimal(100)).toString());

        editBonus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(start !=0){
                    totalPrice.setText(new BigDecimal(totalPriceTicket).subtract(new BigDecimal(totalPriceTicket).multiply(new BigDecimal(s.toString())).divide(new BigDecimal(100))).toString());
                }else{
                    totalPrice.setText(totalPriceTicket);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.payBooking(autheader,totalPrice.getText().toString(),new Long(flightBooking.getId()).toString()
                        ,null,new Long(travelClassId).toString());

                viewModel.response().observe(getActivity(),response -> processResponse(response));
            }
        });




        return inflate;
    }

    private void processResponse(Response response) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                Toast.makeText(getContext(), "Payment successful!", Toast.LENGTH_LONG).show();
                break;

            case ERROR:
                break;
        }
    }


    private void  initKey(){
        autheader = getArguments().getString("AUTORIZATION");
        flightBooking = (FlightBooking)getArguments().getParcelable("FLIGHT_BOOKING");
        travelClassId = getArguments().getString("TRAVEL_CLASS_ID");
        totalPriceTicket = getArguments().getString("TOTAL_PRICE_TICKET") ;

    }

}
