package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.PassengerTicket;
import com.easy.fly.flyeasy.utils.DateFormater;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private List<PassengerTicket> tickets;
    private FlightBooking flightBooking;
    private Context context;

    public TicketAdapter(FlightBooking flightBooking, Context context){
        this.flightBooking = flightBooking;
        this.tickets = flightBooking.getPassengerTickets();
        this.context = context;
    }


    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_ticket,parent,false);
        return new TicketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder holder, int position) {

        holder.airlineName.setText(flightBooking.getFlight().getAirLine().getAirlineName());
        holder.dateTimeFlight.setText(DateFormater.formatDateForUI(flightBooking.getFlight().getDepartDate()));
        holder.flightName.setText(flightBooking.getFlight().getFlightName());
        holder.seat.setText(tickets.get(position).getBoardSeatNumber());
        holder.fullName.setText(tickets.get(position).getPassengerName());

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView airlineName;
        private TextView flightName;
        private TextView dateTimeFlight;
        private TextView seat;
        private TextView fullName;

        public ViewHolder(View itemView) {
            super(itemView);

            airlineName = (TextView) itemView.findViewById(R.id.airline_name);
            flightName = (TextView) itemView.findViewById(R.id.flight_name);
            dateTimeFlight = (TextView) itemView.findViewById(R.id.date_time_flight);
            seat = (TextView) itemView.findViewById(R.id.seat);
            fullName = (TextView) itemView.findViewById(R.id.full_name);

        }
    }
}
