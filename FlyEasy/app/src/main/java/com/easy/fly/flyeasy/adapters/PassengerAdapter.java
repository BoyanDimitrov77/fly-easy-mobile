package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.dto.PassengerDto;

import java.util.ArrayList;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ViewHolder> {

    private ArrayList<PassengerDto> passengers;
    private Context context;
    //private String authorization;

    public PassengerAdapter(ArrayList<PassengerDto> passengers,Context context){
        this.passengers = passengers;
        this.context = context;
        //this.authorization= authorization;
    }

    @NonNull
    @Override
    public PassengerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_passenger,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerAdapter.ViewHolder holder, int position) {

        PassengerDto passengerDto = passengers.get(position);
        holder.passengerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    passengerDto.setPassengerName(holder.passengerName.getText().toString());
                }
            }
        });

        holder.identificationNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    passengerDto.setIdentificationNumber(holder.identificationNumber.getText().toString());
                }
            }
        });

        holder.email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    passengerDto.setEmail(holder.email.getText().toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }

    public ArrayList<PassengerDto> getPassengers(){
        return passengers;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView passengerName;
        private TextView identificationNumber;
        private TextView email;


        public ViewHolder(View itemView) {
            super(itemView);

            passengerName = (TextView) itemView.findViewById(R.id.passengerName);
            identificationNumber = (TextView) itemView.findViewById(R.id.identificationNumber);
            email = (TextView) itemView.findViewById(R.id.passengerEmail);

        }
    }
}
