package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightBooking implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FlightBooking createFromParcel(Parcel in) {
            return new FlightBooking(in);
        }

        public FlightBooking[] newArray(int size) {
            return new FlightBooking[size];
        }
    };

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("flight")
    @Expose
    private Flight flight;
    @SerializedName("booker")
    @Expose
    private User booker;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("passengerTickets")
    @Expose
    private List<PassengerTicket> passengerTickets = null;

    public FlightBooking(Parcel in) {

        this.id = in.readLong();
        this.flight = (Flight) in.readParcelable(Flight.class.getClassLoader());
        this.booker = (User) in.readParcelable(User.class.getClassLoader());
        this.payment = (Payment) in.readParcelable(Payment.class.getClassLoader());
        this.status = in.readString();
        this.passengerTickets = (List<PassengerTicket>)in.readArrayList(PassengerTicket.class.getClassLoader());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PassengerTicket> getPassengerTickets() {
        return passengerTickets;
    }

    public void setPassengerTickets(List<PassengerTicket> passengerTickets) {
        this.passengerTickets = passengerTickets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.flight,flags);
        dest.writeParcelable(this.booker,flags);
        dest.writeParcelable(this.payment,flags);
        dest.writeString(this.status);
        dest.writeArray(this.passengerTickets.toArray());

    }
}
