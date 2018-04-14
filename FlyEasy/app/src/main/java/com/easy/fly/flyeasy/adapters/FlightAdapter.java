package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.db.models.Flight;
import com.easy.fly.flyeasy.di.GlideApp;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

    private ArrayList<Flight> flights;
    private Context context;

    public FlightAdapter (ArrayList<Flight> flights,Context context){
        this.flights = flights;
        this.context = context;
    }

    @NonNull
    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.ViewHolder holder, int position) {

        holder.airlineName.setText(flights.get(position).getAirLine().getAirlineName());
        holder.locationFrom.setText(flights.get(position).getLocationFrom().getName());
        holder.locationTo.setText(flights.get(position).getLocationTo().getName());
        holder.departDate.setText(flights.get(position).getDepartDate());
        holder.arrive.setText(flights.get(position).getArriveDate());
        holder.priceTicket.setText(flights.get(position).getPrice().toString());

/*        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ya29.GlycBbDorEvD_-rnDgRa9pkPJ2e2sQsXmigEsxHBE-rWakIK-ZLyjGtLG1FNbL9x09AGhs2iLZez0xA-aOV0_chdYqDkWxlOBkQU4vH-HfIMfwZ9rNjwtONn88Mv-w")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();


        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .loggingEnabled(true)
                .build();*/

        Picasso.get()
                .load("https://" + flights.get(position).getAirLine().getLogo().getThumbnailPicture().getValue())
                .placeholder(R.drawable.airplane_icon)
                .error(R.drawable.airplane_icon)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.airLineLogo);
        /*StatsSnapshot picassoStats = picasso.getSnapshot();

        Log.d("Picasso Stats", picassoStats.toString());*/

//Glide
/*        LazyHeaders auth = new LazyHeaders.Builder() // can be cached in a field and reused
                .addHeader("Authorization", "Bearer ya29.GlycBQ1ZYZXT3ug5O9oa0qnjOp7M-9-vSC0zWGy0KgyuhKyiZddyUPtcxb465RVP46nhA5W1Q41wzNsEHYUsd3ORsz0RSS_4GX7wh7Q-yAzbu9afEKzq1ADv4S9iLw")
                .build();

        GlideApp.with(context)
                //.load(new GlideUrl("https://" + flights.get(position).getAirLine().getLogo().getThumbnailPicture().getValue(), auth)) // GlideUrl is created anyway so there's no extra objects allocated
                .load("http://i.imgur.com/DvpvklR.png")
                .into(holder.airLineLogo);*/


    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView airlineName;
        private TextView locationFrom;
        private TextView locationTo;
        private TextView departDate;
        private TextView arrive;
        private TextView priceTicket;
        private ImageView airLineLogo;

        public ViewHolder(View itemView) {
            super(itemView);

            airlineName = (TextView) itemView.findViewById(R.id.airline_name);
            locationFrom = (TextView) itemView.findViewById(R.id.location_from);
            locationTo = (TextView) itemView.findViewById(R.id.location_to);
            departDate = (TextView) itemView.findViewById(R.id.depart_date);
            arrive = (TextView) itemView.findViewById(R.id.arrive);
            priceTicket = (TextView) itemView.findViewById(R.id.price_ticket);
            airLineLogo = (ImageView) itemView.findViewById(R.id.airline_logo);

        }
    }
}
