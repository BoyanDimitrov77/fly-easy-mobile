package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.db.models.Hotel;
import com.easy.fly.flyeasy.fragments.BookingFragment;
import com.easy.fly.flyeasy.fragments.HotelDetailsFragment;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> implements Filterable {

    private ArrayList<Hotel> hotels;
    private  List<Hotel> filteredHotels;
    private String accesTokenGD;
    private Context context;
    private FragmentManager fragmentManager;

    public HotelAdapter(CombineModel data, Context context, FragmentManager fragmentManager){
        this.hotels = (ArrayList<Hotel>) data.getDataT1();
        this.filteredHotels = (ArrayList<Hotel>) data.getDataT1();
        this.accesTokenGD = ((BasicModel) data.getDataT2()).getData();
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_hotel,parent,false);
        HotelAdapter.ViewHolder viewHolder = new HotelAdapter.ViewHolder(view);
        view.setOnClickListener((v)->{
            int position = viewHolder.getAdapterPosition();

            Bundle bundle = new Bundle();
            bundle.putParcelable("HOTEL",filteredHotels.get(position));
            bundle.putString("ACCES_TOCKENT_GD",accesTokenGD);

            HotelDetailsFragment hotelDetailsFragment = new HotelDetailsFragment();
            hotelDetailsFragment.setArguments(bundle);

            //start fragment
            android.support.v4.app.FragmentManager fragmentM  = fragmentManager;
            int containerId = R.id.container;
            fragmentM.beginTransaction()
                    .replace(containerId,hotelDetailsFragment)
                    .commitAllowingStateLoss();
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Hotel hotel = filteredHotels.get(position);
        holder.hotelName.setText(hotel.getHotelName());
        holder.hotelDescription.setText(hotel.getDescription());
        holder.location.setText(hotel.getLocation().getName());

        if(hotel.getPictures().size()>0){
            Glide.with(context)
                    .load(HeaderAtuhenticationGlide.loadUrl(hotel.getPictures().get(0).getThumbnailPicture().getValue(),accesTokenGD))// GlideUrl is created anyway so there's no extra objects allocated
                    .into(holder.hotelPicture);
        }else{
            holder.hotelPicture.setImageDrawable(null);
        }

    }

    @Override
    public int getItemCount() {
        return filteredHotels.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredHotels = hotels;
                } else {
                    List<Hotel> filteredList = new ArrayList<>();
                    for (Hotel row : hotels) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getHotelName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredHotels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredHotels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredHotels = (ArrayList<Hotel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView hotelName;
        private TextView hotelDescription;
        private TextView location;
        private CircleImageView hotelPicture;

        public ViewHolder(View itemView) {
            super(itemView);

            hotelName = (TextView) itemView.findViewById(R.id.hotel_name);
            hotelDescription = (TextView) itemView.findViewById(R.id.hotel_description);
            location = (TextView) itemView.findViewById(R.id.hotel_location);
            hotelPicture = (CircleImageView) itemView.findViewById(R.id.hotel_picture);


        }
    }
}
