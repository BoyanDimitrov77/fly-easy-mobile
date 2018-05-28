package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.fragments.HotelDetailsFragment;
import com.easy.fly.flyeasy.fragments.TicketDetailsFragment;

import java.util.ArrayList;

public class MyFlightsAdapter extends RecyclerView.Adapter<MyFlightsAdapter.ViewHolder> {

    private ArrayList<FlightBooking> myFlights;
    private String accesTokenGD;
    private Context context;
    private String authorization;
    private FragmentManager fragmentManager;

    public MyFlightsAdapter (CombineModel data, Context context,FragmentManager fragmentManager){
        this.myFlights = (ArrayList<FlightBooking>) data.getDataT1();
        this.accesTokenGD = ((BasicModel) data.getDataT2()).getData();
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MyFlightsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_my_flight,parent,false);
        MyFlightsAdapter.ViewHolder viewHolder = new MyFlightsAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

                Bundle bundle = new Bundle();
                bundle.putParcelable("FLIGHT_BOOKING",myFlights.get(position));

                TicketDetailsFragment ticketDetailsFragment = new TicketDetailsFragment();
                ticketDetailsFragment.setArguments(bundle);

                //start fragment
                android.support.v4.app.FragmentManager fragmentM  = fragmentManager;
                int containerId = R.id.container;
                fragmentM.beginTransaction()
                        .replace(containerId,ticketDetailsFragment)
                        .commitAllowingStateLoss();
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFlightsAdapter.ViewHolder holder, int position) {

        holder.airlineName.setText(myFlights.get(position).getFlight().getAirLine().getAirlineName());
        holder.locationFrom.setText(myFlights.get(position).getFlight().getLocationFrom().getName());
        holder.locationTo.setText(myFlights.get(position).getFlight().getLocationTo().getName());
        holder.departDate.setText(myFlights.get(position).getFlight().getDepartDate());
        holder.arrive.setText(myFlights.get(position).getFlight().getArriveDate());
        holder.priceTicket.setText(myFlights.get(position).getPayment().getAmount().toString());

        if(myFlights.get(position).getStatus().equals("CONFIRMED")){
            holder.button_info.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.button_info.setText("CONFIRMED");
        }else if(myFlights.get(position).getStatus().equals("WAITING")){
            holder.button_info.setBackgroundColor(Color.RED);
            holder.button_info.setText("WAITING");
        }else if (myFlights.get(position).getStatus().equals("CANCELLED")){
            holder.button_info.setBackgroundColor(Color.BLACK);
            holder.button_info.setText("CANCELLED");

        }
        Glide.with(context)
                .load(HeaderAtuhenticationGlide.loadUrl(myFlights.get(position).getFlight().getAirLine().getLogo().getThumbnailPicture().getValue(),accesTokenGD)) // GlideUrl is created anyway so there's no extra objects allocated
                .into(holder.airLineLogo);


    }

    @Override
    public int getItemCount() {
        return myFlights.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView airlineName;
        private TextView locationFrom;
        private TextView locationTo;
        private TextView departDate;
        private TextView arrive;
        private TextView priceTicket;
        private ImageView airLineLogo;
        private Button button_info;

        public ViewHolder(View itemView) {
            super(itemView);

            airlineName = (TextView) itemView.findViewById(R.id.airline_name);
            locationFrom = (TextView) itemView.findViewById(R.id.location_from);
            locationTo = (TextView) itemView.findViewById(R.id.location_to);
            departDate = (TextView) itemView.findViewById(R.id.depart_date);
            arrive = (TextView) itemView.findViewById(R.id.arrive);
            priceTicket = (TextView) itemView.findViewById(R.id.price_ticket);
            airLineLogo = (ImageView) itemView.findViewById(R.id.airline_logo);
            button_info = (Button) itemView.findViewById(R.id.button_info);

        }
    }
}
