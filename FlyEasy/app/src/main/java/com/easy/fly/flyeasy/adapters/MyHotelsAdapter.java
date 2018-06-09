package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.HotelBook;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyHotelsAdapter extends RecyclerView.Adapter<MyHotelsAdapter.ViewHolder> {

    private ArrayList<HotelBook> myhotels;
    private String accesTokenGD;
    private Context context;

    public MyHotelsAdapter(CombineModel data, Context context){
        this.myhotels = (ArrayList<HotelBook>) data.getDataT1();
        this.accesTokenGD = ((BasicModel) data.getDataT2()).getData();
        this.context = context;
    }

    @NonNull
    @Override
    public MyHotelsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_my_hotel_booked,parent,false);
        MyHotelsAdapter.ViewHolder viewHolder = new MyHotelsAdapter.ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHotelsAdapter.ViewHolder holder, int position) {

        holder.hotelName.setText(myhotels.get(position).getHotelRoom().getReservedHotel().getHotelName());
        holder.hotelLocation.setText(myhotels.get(position).getHotelRoom().getReservedHotel().getLocation().getName());
        holder.typeHotelRoomBooked.setText(myhotels.get(position).getHotelRoom().getTypeRoom());
        holder.price.setText("$"+myhotels.get(position).getPayment().getAmount().toString());

        if(myhotels.get(position).getStatus().equals("CONFIRMED")){
            holder.buttonInfo.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.buttonInfo.setText("CONFIRMED");
        }else if(myhotels.get(position).getStatus().equals("WAITING")){
            holder.buttonInfo.setBackgroundColor(Color.RED);
            holder.buttonInfo.setText("WAITING");
        }else if (myhotels.get(position).getStatus().equals("CANCELLED")){
            holder.buttonInfo.setBackgroundColor(Color.BLACK);
            holder.buttonInfo.setText("CANCELLED");

        }
        Glide.with(context)
                .load(HeaderAtuhenticationGlide.loadUrl(myhotels.get(position).getHotelRoom().getReservedHotel().getPictures().get(0).getThumbnailPicture().getValue(),accesTokenGD)) // GlideUrl is created anyway so there's no extra objects allocated
                .into(holder.hotelPicture);

    }

    @Override
    public int getItemCount() {
        return myhotels.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        private CircleImageView hotelPicture;
        private TextView hotelName;
        private TextView hotelLocation;
        private TextView typeHotelRoomBooked;
        private TextView price;
        private Button buttonInfo;

        public ViewHolder(View itemView) {
            super(itemView);

            hotelPicture = (CircleImageView) itemView.findViewById(R.id.hotel_picture);
            hotelName = (TextView) itemView.findViewById(R.id.hotel_name);
            hotelLocation = (TextView) itemView.findViewById(R.id.hotel_location);
            typeHotelRoomBooked = (TextView) itemView.findViewById(R.id.type_booked_room);
            price = (TextView) itemView.findViewById(R.id.price_hotel_room);
            buttonInfo = (Button) itemView.findViewById(R.id.button_info);

        }
    }
}
