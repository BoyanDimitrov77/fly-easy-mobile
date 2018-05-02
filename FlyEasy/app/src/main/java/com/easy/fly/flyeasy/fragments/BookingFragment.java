package com.easy.fly.flyeasy.fragments;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.FlightAdapter;
import com.easy.fly.flyeasy.adapters.PassengerAdapter;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.TravelClass;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.PassengerDto;
import com.easy.fly.flyeasy.utils.DateFormater;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.BookingViewModel;
import com.easy.fly.flyeasy.viewmodel.RegisterUserViewModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.math.BigDecimal;

import java.util.ArrayList;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment implements Injectable {

    @BindView(R.id.booking_from_location)
    TextView bookingLocationFrom;

    @BindView(R.id.booking_to_location)
    TextView bookingLocationTo;

    @BindView(R.id.booking_depart)
    TextView bookingDepart;

    @BindView(R.id.booking_arrive)
    TextView bookingArrive;

    @BindView(R.id.booking_depart_date)
    TextView bookingDepartDate;

    @BindView(R.id.booking_price_ticket)
    TextView bookingPriceTicket;

    @BindView(R.id.airline_logo)
    ImageView airlineLogo;

    @BindView(R.id.airline_name)
    TextView airlineName;

    @BindView(R.id.spinnerTicket)
    Spinner spinnerTicket;

    @BindView(R.id.spinnerTravelClass)
    Spinner spinnerTravelClass;

    @BindView(R.id.book_button)
    Button bookButton;

    private int ticketNum;

    private BigDecimal basePriceFlight;

    private BookingViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    //private String authHeader;

    private Flight flight;

    private TravelClass travelClass;

    private BigDecimal totalPrice;

    private SessionManager sessionManager;

    private String userAthenticationHeader;

    private String accesTokenGD;

    public BookingFragment() {
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

        View inflate = inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this,inflate);

        sessionManager = new SessionManager(getContext());

        initKey();

        airlineName.setText(flight.getAirLine().getAirlineName());
        bookingLocationFrom.setText(flight.getLocationFrom().getName());
        bookingLocationTo.setText(flight.getLocationTo().getName());
        bookingDepart.setText(DateFormater.getHourFromDate(flight.getDepartDate().toString()) );
        bookingArrive.setText(DateFormater.getHourFromDate(flight.getArriveDate().toString()));
        bookingDepartDate.setText(DateFormater.formatDateForUI(flight.getDepartDate().toString()));
        bookingPriceTicket.setText("$"+ flight.getPrice().toString());

        basePriceFlight = flight.getPrice();

        Glide.with(getContext())
                .load(HeaderAtuhenticationGlide.loadUrl(flight.getAirLine().getLogo().getThumbnailPicture().getValue(),accesTokenGD)) // GlideUrl is created anyway so there's no extra objects allocated
                .into(airlineLogo);

        //spinner ticket
        ArrayAdapter<CharSequence> adapterTicket = ArrayAdapter.createFromResource(getContext(),
                R.array.numberTicket, android.R.layout.simple_spinner_item);
        adapterTicket.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTicket.setAdapter(adapterTicket);
        spinnerTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ticketNum = Integer.parseInt((String) parent.getItemAtPosition(position));
                totalPrice = basePriceFlight.multiply(BigDecimal.valueOf(ticketNum));
                bookingPriceTicket.setText(totalPrice.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner travelClass

        ArrayAdapter<CharSequence> adapterTravelClass = ArrayAdapter.createFromResource(getContext(),
                R.array.travelClass, android.R.layout.simple_spinner_item);
        adapterTravelClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTravelClass.setAdapter(adapterTravelClass);
        spinnerTravelClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTravelClass = (String)parent.getItemAtPosition(position);
                travelClass = flight.getTravelClasses().stream().filter(tc -> tc.getTravelClass().equals(selectedTravelClass)).findFirst().get();
                basePriceFlight = travelClass.getPrice();
                bookingPriceTicket.setText(basePriceFlight.multiply(BigDecimal.valueOf(ticketNum)).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.bookFlight(userAthenticationHeader,flight.getId());
                viewModel.response().observe(getActivity(),response->processResponse(response,travelClass.getId(),totalPrice));
            }
        });

        return inflate;
    }

    private void processResponse(Response response, long travelClassId, BigDecimal totalPrice) {
        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:

                Bundle bundle = new Bundle();
                bundle.putInt("TICKET_NUMBER",ticketNum);
                //bundle.putString("AUTORIZATION",authHeader);
                bundle.putLong("FLIGHT_BOOK_ID",((FlightBooking)response.data).getId());
                bundle.putLong("TRAVEL_CLASS_ID",travelClassId);
                bundle.putString("TOTAL_PRICE_TICKET",totalPrice.toString());

                PassengerFragment passengerFragment = new PassengerFragment();
                passengerFragment.setArguments(bundle);
                //start fragment
                android.support.v4.app.FragmentManager fragmentManager = this.getFragmentManager();
                int containerId = R.id.container;
                fragmentManager.beginTransaction()
                        .replace(containerId,passengerFragment)
                        .commitAllowingStateLoss();

                break;

            case ERROR:
                break;
        }


    }

    private void initKey(){
        userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
        flight = (Flight)getArguments().getParcelable("FLIGHT");
        accesTokenGD = (String)getArguments().get("ACCES_TOCKENT_GD");

    }

}
