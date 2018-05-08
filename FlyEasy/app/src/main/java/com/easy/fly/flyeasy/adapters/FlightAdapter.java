package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.BookingActivity;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.db.models.BasicModel;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.Flight;

import java.util.ArrayList;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

    private ArrayList<Flight> flights;
    private String accesTokenGD;
    private Context context;
    private String authorization;

    public FlightAdapter (CombineModel data, Context context){
        this.flights = (ArrayList<Flight>) data.getDataT1();
        this.accesTokenGD = ((BasicModel) data.getDataT2()).getData();
        this.context = context;
        //this.authorization = authorization;
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

        holder.bookBbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("FLIGHT",flights.get(position));
                intent.putExtra("ACCES_TOCKENT_GD",accesTokenGD);
                //intent.putExtra("AUTORIZATION",authorization);
                context.startActivity(intent);

            }
        });


/*        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer "+accesTokenGD)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();


        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .loggingEnabled(true)
                .build();

        picasso
                .load("https://" + flights.get(position).getAirLine().getLogo().getThumbnailPicture().getValue())
                //.load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.drawable.airplane_icon)
                .error(R.drawable.airplane_icon)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.airLineLogo);

        StatsSnapshot picassoStats = picasso.getSnapshot();

        Log.d("Picasso Stats", picassoStats.toString());*/

//Glide
        Glide.with(context)
                .load(HeaderAtuhenticationGlide.loadUrl(flights.get(position).getAirLine().getLogo().getThumbnailPicture().getValue(),accesTokenGD)) // GlideUrl is created anyway so there's no extra objects allocated
                .into(holder.airLineLogo);


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
        private Button bookBbutton;

        public ViewHolder(View itemView) {
            super(itemView);

            airlineName = (TextView) itemView.findViewById(R.id.airline_name);
            locationFrom = (TextView) itemView.findViewById(R.id.location_from);
            locationTo = (TextView) itemView.findViewById(R.id.location_to);
            departDate = (TextView) itemView.findViewById(R.id.depart_date);
            arrive = (TextView) itemView.findViewById(R.id.arrive);
            priceTicket = (TextView) itemView.findViewById(R.id.price_ticket);
            airLineLogo = (ImageView) itemView.findViewById(R.id.airline_logo);
            bookBbutton = (Button) itemView.findViewById(R.id.book_button);

        }
    }
}
