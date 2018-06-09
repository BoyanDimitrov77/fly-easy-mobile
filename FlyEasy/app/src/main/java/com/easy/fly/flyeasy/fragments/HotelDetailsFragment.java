package com.easy.fly.flyeasy.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.FlightAdapter;
import com.easy.fly.flyeasy.adapters.GalleryAdapter;
import com.easy.fly.flyeasy.adapters.HotelAdapter;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.Hotel;
import com.easy.fly.flyeasy.db.models.HotelRoom;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.HotelViewModel;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelDetailsFragment extends Fragment implements Injectable {

    @BindView(R.id.hotel_picture_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.hotel_name)
    TextView hotelName;

    @BindView(R.id.price_hotel)
    TextView priceHotel;

    @BindView(R.id.spinnerRoom)
    Spinner spinnerRoom;

    @BindView(R.id.hotelBookBtn)
    Button hotelBookButton;

    private GalleryAdapter adapter;

    private Hotel hotel;

    private String accesTokenGD;

    private String userAthenticationHeader;

    private BigDecimal totalPrice;

    private HotelRoom selectedHotelRoom;

    private SessionManager sessionManager;

    private HotelViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    public HotelDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(HotelViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_hotel_details, container, false);
        ButterKnife.bind(this,inflate);
        sessionManager = new SessionManager(getContext());

        initKey();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new GalleryAdapter(getContext(),hotel.getPictures(),accesTokenGD);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("images", (ArrayList<? extends Parcelable>) hotel.getPictures());
                bundle.putInt("position", position);
                bundle.putString("ACCES_TOCKENT_GD",accesTokenGD);

                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "slideshow");

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        hotelName.setText(hotel.getHotelName());

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expandableTextViewHotelDescription = (ExpandableTextView) inflate.findViewById(R.id.expandable_layout)
                .findViewById(R.id.expand_text_view);

    // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expandableTextViewHotelDescription.setText(hotel.getDescription());

        totalPrice = hotel.getHotelRooms().get(0).getPrice();
        priceHotel.setText("$" + totalPrice.toString());

        //Spinner
        Object[] hotelRoomsNameType = hotel.getHotelRooms().stream().map(HotelRoom::getTypeRoom).collect(Collectors.toList()).toArray();

        ArrayAdapter<CharSequence> adapterHotelRoomsNameType = new ArrayAdapter(getContext(),
                 android.R.layout.simple_spinner_item,hotelRoomsNameType);
        adapterHotelRoomsNameType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(adapterHotelRoomsNameType);
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTypeRoom = (String)parent.getItemAtPosition(position);
                selectedHotelRoom = hotel.getHotelRooms().stream().filter(hr -> hr.getTypeRoom().equals(selectedTypeRoom)).findFirst().get();
                totalPrice = selectedHotelRoom.getPrice();
                priceHotel.setText("$"+totalPrice.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hotelBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.hotelBook(userAthenticationHeader,selectedHotelRoom.getId(),totalPrice.toString());
                viewModel.response().observe(getActivity(),response->processResponse(response));
            }
        });

        return inflate;
    }

    private void processResponse(Response response) {
        Bundle bundle = new Bundle();
        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                moveToPaymentStatusFragment(bundle,true);
                break;

            case ERROR:
                moveToPaymentStatusFragment(bundle,false);
                break;
        }
    }

    private void initKey(){
        hotel = (Hotel)getArguments().getParcelable("HOTEL");
        accesTokenGD = (String)getArguments().get("ACCES_TOCKENT_GD");
        userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
    }

    private void moveToPaymentStatusFragment(Bundle bundle,boolean paymentStatus){
        bundle.putBoolean("IS_CONFIRMED",paymentStatus);

        PaymentStatusFragment paymentStatusFragment = new PaymentStatusFragment();
        paymentStatusFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = this.getFragmentManager();
        int containerId = R.id.container;
        fragmentManager.beginTransaction()
                .replace(containerId,paymentStatusFragment)
                .commitAllowingStateLoss();
    }

}
